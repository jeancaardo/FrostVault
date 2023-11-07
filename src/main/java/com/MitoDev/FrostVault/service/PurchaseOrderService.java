package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.exception.custom.*;
import com.MitoDev.FrostVault.model.dto.*;
import com.MitoDev.FrostVault.model.entity.*;
import com.MitoDev.FrostVault.model.entity.enums.PurchaseOrderStatus;
import com.MitoDev.FrostVault.repository.*;
import com.MitoDev.FrostVault.service.interfaces.IPurchaseOrderService;
import com.MitoDev.FrostVault.util.UserUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderRepository purchaseOrderRepository;

    private final IProductRepository productRepository;

    private final IBatchRepository batchRepository;

    private final IUserRepository userRepository;

    public PurchaseOrderService(IPurchaseOrderRepository purchaseOrderRepository, IProductRepository productRepository, IBatchRepository batchRepository, IPurchaseOrderDetailRepository purchaseOrderDetailRepository, IUserRepository userRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
    }


    public PurchaseOrderTotalPriceResponseDTO initializePurchaseOrder() {

        // Retrieving context data about user who requested
        User contextUser = UserUtils.getUserFromContext();

        // Searching for user existing in db, otherwise return error
        var userPersisted = userRepository.findById(contextUser.getId()).orElseThrow(
                () -> {throw new LoggedUserDoesNotExistException(contextUser.getId());}
        );

        // Searching for another purchaseOrder with status "cart" existing right now. If so return error

        if(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(contextUser.getId(), PurchaseOrderStatus.CART).isPresent())
            throw new PurchaseOrderAlreadyInProcess(userPersisted.getId());

        purchaseOrderRepository.save(
                PurchaseOrder.builder()
                        .date(LocalDate.now())
                        .statusCode(PurchaseOrderStatus.CART)
                        .purchaseOrderDetails(List.of())
                        .user(userPersisted)
                        .build()
        );

        return PurchaseOrderTotalPriceResponseDTO.builder()
                .totalPrice(0D)
                .build();
    }

    public PurchaseOrderTotalPriceResponseDTO addProductToPurchaseOrder(ProductDTO product) {

        // Retrieving context data about user who requested
        User contextUser = UserUtils.getUserFromContext();

        var prodPersisted = productRepository.findById(product.getProductId())
                .orElseThrow(() -> {
                    throw new EntityDoesNotExistException(Product.class, product.getProductId());
                });
        var batchesAvailable = batchRepository.findByProductIdProductEqualsAndDueDateGreaterThanOrderByDueDateAsc(product.getProductId(), LocalDate.now().plus(3, ChronoUnit.WEEKS));
        if (batchesAvailable.stream().mapToInt(Batch::getCurrentQuantity).sum() < product.getQuantity())
            throw new NotEnoughProductQuantityException(prodPersisted.getIdProduct());

        var purchaseOrder =
                purchaseOrderRepository.findByUserIdAndStatusCodeEquals(contextUser.getId(), PurchaseOrderStatus.CART)
                        .orElseThrow(
                                () -> {throw new CurrentPurchaseOrderNotExistException(contextUser.getId());}
                        );

        var purchaseOrderDetail =
                PurchaseOrderDetail.builder()
                        .product(prodPersisted)
                        .quantity(product.getQuantity())
                        .build();

        var purchaseOrderDetailsPersisted = new ArrayList<>(purchaseOrder.getPurchaseOrderDetails());
        purchaseOrderDetailsPersisted.add(purchaseOrderDetail);
        purchaseOrder.setPurchaseOrderDetails(purchaseOrderDetailsPersisted);

        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        return PurchaseOrderTotalPriceResponseDTO.builder()
                .totalPrice(
                        purchaseOrder.getPurchaseOrderDetails().stream()
                                .mapToDouble(
                                        p -> p.getQuantity() * p.getProduct().getPrice()
                                )
                                .sum()
                )
                .build();
    }

    public PurchaseOrderTotalPriceResponseDTO deleteProductFromPurchaseOrder(ProductDTO product) {

        // Retrieving context data about user who requested
        User contextUser = UserUtils.getUserFromContext();

        var prodPersisted = productRepository.findById(product.getProductId())
                .orElseThrow(() -> {
                    throw new EntityDoesNotExistException(Product.class, product.getProductId());
                });

        var purchaseOrder =
                purchaseOrderRepository.findByUserIdAndStatusCodeEquals(contextUser.getId(), PurchaseOrderStatus.CART)
                        .orElseThrow(
                                () -> {throw new CurrentPurchaseOrderNotExistException(contextUser.getId());}
                        );

        if (purchaseOrder.getPurchaseOrderDetails().stream()
                .noneMatch(p -> Objects.equals(p.getProduct().getIdProduct(), product.getProductId())))
            throw new ProductNotPresentInCartException(product.getProductId());

        var purchaseOrderDetailsPersisted = new ArrayList<>(purchaseOrder.getPurchaseOrderDetails());
        purchaseOrderDetailsPersisted.removeIf(p-> Objects.equals(p.getProduct().getIdProduct(), product.getProductId()));
        purchaseOrder.setPurchaseOrderDetails(purchaseOrderDetailsPersisted);

        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        return PurchaseOrderTotalPriceResponseDTO.builder()
                .totalPrice(
                        purchaseOrder.getPurchaseOrderDetails().stream()
                                .mapToDouble(
                                        p -> p.getQuantity() * p.getProduct().getPrice()
                                )
                                .sum()
                )
                .build();
    }

    public PurchaseOrderExecutedDTO executePurchaseOrder() {
        // Retrieving context data about user who requested
        User contextUser = UserUtils.getUserFromContext();

        var purchaseOrder =
                purchaseOrderRepository.findByUserIdAndStatusCodeEquals(contextUser.getId(), PurchaseOrderStatus.CART)
                        .orElseThrow(
                                () -> {throw new CurrentPurchaseOrderNotExistException(contextUser.getId());}
                        );

        for(var p : purchaseOrder.getPurchaseOrderDetails()) {
            var prodPersisted = productRepository.findById(p.getProduct().getIdProduct())
                    .orElseThrow(() -> {
                        throw new EntityDoesNotExistException(Product.class, p.getProduct().getIdProduct());
                    });
            var batchesAvailable = batchRepository.findByProductIdProductEqualsAndDueDateGreaterThanOrderByDueDateAsc(p.getProduct().getIdProduct(), LocalDate.now().plus(3, ChronoUnit.WEEKS));
            if (batchesAvailable.stream().mapToInt(Batch::getCurrentQuantity).sum() < p.getQuantity())
                throw new NotEnoughProductQuantityException(prodPersisted.getIdProduct());

            Integer quantityRemaining = p.getQuantity();
            List<Batch> batchesResultant = new ArrayList<>();
            for (var batch : batchesAvailable) {
                if (batch.getCurrentQuantity() <= p.getQuantity()) {
                    quantityRemaining -= batch.getCurrentQuantity();
                    batch.setCurrentQuantity(0);
                } else
                    batch.setCurrentQuantity(batch.getCurrentQuantity() - quantityRemaining);
                batchesResultant.add(batch);
            }


            batchRepository.saveAll(batchesResultant);
        }

        purchaseOrder.setStatusCode(PurchaseOrderStatus.CONFIRMED);

        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        return PurchaseOrderExecutedDTO.builder()
                .amountExecuted(
                        purchaseOrder.getPurchaseOrderDetails().stream()
                                .mapToDouble(
                                        p -> p.getQuantity() * p.getProduct().getPrice()
                                )
                                .sum()
                )
                .cardNumber(purchaseOrder.getUser().getCardNumber())
                .build();
    }

    @Override
    public ProductsReponseDTO getAllProductsInPurchaseOrder() {
        // Retrieving context data about user who requested
        User contextUser = UserUtils.getUserFromContext();

        var purchaseOrder = purchaseOrderRepository.findByUserIdAndStatusCodeEquals(contextUser.getId(), PurchaseOrderStatus.CART)
                .orElseThrow(
                        () -> {throw new CurrentPurchaseOrderNotExistException(contextUser.getId());}
                );

        if (purchaseOrder.getPurchaseOrderDetails().isEmpty())
            throw new NoProductsException();
        else
            return new ProductsReponseDTO(
                    purchaseOrder.getPurchaseOrderDetails().stream()
                            .map((p)->
                                    ProductDTO.builder()
                                            .productId(p.getProduct().getIdProduct())
                                            .quantity(p.getQuantity())
                                            .build()
                            ).toList()
            );
    }

}
