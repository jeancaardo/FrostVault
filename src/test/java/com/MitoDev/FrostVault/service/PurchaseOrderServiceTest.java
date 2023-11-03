package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.dataFactories.*;
import com.MitoDev.FrostVault.exception.custom.*;
import com.MitoDev.FrostVault.model.dto.ProductDTO;
import com.MitoDev.FrostVault.model.dto.PurchaseOrderRequestDTO;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.PurchaseOrder;
import com.MitoDev.FrostVault.model.entity.User;
import com.MitoDev.FrostVault.model.entity.enums.PurchaseOrderStatus;
import com.MitoDev.FrostVault.repository.IBatchRepository;
import com.MitoDev.FrostVault.repository.IProductRepository;
import com.MitoDev.FrostVault.repository.IPurchaseOrderRepository;
import com.MitoDev.FrostVault.repository.IUserRepository;
import com.MitoDev.FrostVault.service.interfaces.IPurchaseOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IPurchaseOrderRepository purchaseOrderRepository;
    @Mock
    private IProductRepository productRepository;
    @Mock
    private IBatchRepository batchRepository;
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private PurchaseOrderService purchaseOrderService;


    @BeforeEach()
    void setup() throws JsonProcessingException {
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));
    }

    @Test
    @DisplayName("Should return a total price of 0 because it's a new cart")
    void initializePurchaseOrder() {
        // arrange
        var expected = PurchaseOrderFactory.totalOfBrandNewCart();

        // act
        when(userRepository.findById(UserFactory.user1().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.empty());
        when(purchaseOrderRepository.save(PurchaseOrderFactory.getNewPurchaseOrder())).thenReturn(PurchaseOrderFactory.getPurchaseOrderCreated());
        var obtained = purchaseOrderService.initializePurchaseOrder();

        // assert
        Assertions.assertEquals(expected, obtained);

    }

    @Test
    @DisplayName("Should throw exception because of user already having a cart initialized")
    void initializePurchaseOrder2() {
        // arrange
        var expected = PurchaseOrderFactory.totalOfBrandNewCart();

        // act
        when(userRepository.findById(UserFactory.user1().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.of(PurchaseOrderFactory.getPurchaseOrderCreated()));
        Assertions.assertThrows(PurchaseOrderAlreadyInProcess.class, () -> purchaseOrderService.initializePurchaseOrder());


    }

    @Test
    @DisplayName("Should return the sum of prices as total price because a product was added")
    void addProductToPurchaseOrder() {

        //arrange
        ProductDTO newProduct = ProductFactory.productDTO1();
        var expected = PurchaseOrderFactory.totalOfAddingProduct1();

        // act
        when(productRepository.findById(newProduct.getProductId())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findByProductIdEqualsAndDueDateGreaterThanOrderByDueDateAscending(newProduct.getProductId(), LocalDate.now().plus(3, ChronoUnit.WEEKS)))
                .thenReturn(List.of(BatchFactory.batchPersisted1()));
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.of(PurchaseOrderFactory.getPurchaseOrderCreated()));
        when(purchaseOrderRepository.save(PurchaseOrderFactory.getPurchaseOrderCreatedAddingOneProduct())).thenReturn(PurchaseOrderFactory.getPurchaseOrderCreatedWithOneProductPersisted());

        var obtained = purchaseOrderService.addProductToPurchaseOrder(newProduct);

        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should throw exception because product does not exist")
    void addProductToPurchaseOrder2() {

        //arrange
        ProductDTO newProduct = ProductFactory.productDTO1();

        // act
        when(productRepository.findById(newProduct.getProductId())).thenReturn(Optional.empty());

        // assert

        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> purchaseOrderService.addProductToPurchaseOrder(newProduct));
    }

    @Test
    @DisplayName("Should throw exception because of not enough capacity available")
    void addProductToPurchaseOrder3() {

        //arrange
        ProductDTO newProduct = ProductFactory.productDTO1();
        var expected = PurchaseOrderFactory.totalOfAddingProduct1();

        // act
        when(productRepository.findById(newProduct.getProductId())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findByProductIdEqualsAndDueDateGreaterThanOrderByDueDateAscending(newProduct.getProductId(), LocalDate.now().plus(3, ChronoUnit.WEEKS)))
                .thenReturn(List.of());

        // assert

        Assertions.assertThrows(NotEnoughProductQuantityException.class, ()-> purchaseOrderService.addProductToPurchaseOrder(newProduct));

    }

    @Test
    @DisplayName("Should throw because of cart not existing")
    void addProductToPurchaseOrder4() {

        //arrange
        ProductDTO newProduct = ProductFactory.productDTO1();
        var expected = PurchaseOrderFactory.totalOfAddingProduct1();

        // act
        when(productRepository.findById(newProduct.getProductId())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findByProductIdEqualsAndDueDateGreaterThanOrderByDueDateAscending(newProduct.getProductId(), LocalDate.now().plus(3, ChronoUnit.WEEKS)))
                .thenReturn(List.of(BatchFactory.batchPersisted1()));
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.empty());

        // assert

        Assertions.assertThrows(CurrentPurchaseOrderNotExistException.class, ()-> purchaseOrderService.addProductToPurchaseOrder(newProduct));
    }

    @Test
    @DisplayName("Should return new total based on all products except the deleted one")
    void deleteProductFromPurchaseOrder() {
        // arrange
        var newProduct = ProductFactory.productDTO1();
        var expected = PurchaseOrderFactory.totalOfBrandNewCart();
        // act
        when(productRepository.findById(newProduct.getProductId())).thenReturn(Optional.of(ProductFactory.product1()));
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.of(PurchaseOrderFactory.getPurchaseOrderCreatedWithOneProductPersisted()));
        when(purchaseOrderRepository.save(PurchaseOrderFactory.getPurchaseOrderCreated())).thenReturn(PurchaseOrderFactory.getPurchaseOrderCreated());

        var obtained = purchaseOrderService.deleteProductFromPurchaseOrder(newProduct);
        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should throw exception because of product not existing")
    void deleteProductFromPurchaseOrder2() {
        // arrange
        var newProduct = ProductFactory.productDTO1();
        var expected = PurchaseOrderFactory.totalOfBrandNewCart();
        // act
        when(productRepository.findById(newProduct.getProductId())).thenReturn(Optional.empty());

        // assert

        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> purchaseOrderService.deleteProductFromPurchaseOrder(newProduct));

    }

    @Test
    @DisplayName("Should throw exception because of product not existing")
    void deleteProductFromPurchaseOrder3() {
        // arrange
        var newProduct = ProductFactory.productDTO1();
        var expected = PurchaseOrderFactory.totalOfBrandNewCart();
        // act
        when(productRepository.findById(newProduct.getProductId())).thenReturn(Optional.of(ProductFactory.product1()));
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.empty());

        // assert

        Assertions.assertThrows(CurrentPurchaseOrderNotExistException.class, ()-> purchaseOrderService.deleteProductFromPurchaseOrder(newProduct));

    }

    @Test
    @DisplayName("Should return a dto with total executed and user card number info")
    void executePurchaseOrder() {
        // arrange
        var expected = PurchaseOrderFactory.purchaseOrderExecutedDTO();

        // act
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.of(PurchaseOrderFactory.getPurchaseOrderCreatedWithOneProductPersisted()));
        when(productRepository.findById(ProductFactory.productDTO1().getProductId())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findByProductIdEqualsAndDueDateGreaterThanOrderByDueDateAscending(ProductFactory.productDTO1().getProductId(), LocalDate.now().plus(3, ChronoUnit.WEEKS))).thenReturn(BatchFactory.listOfBatchesPersisted());
        when(batchRepository.saveAll(List.of(BatchFactory.batchPersisted1With0Quantity()))).thenReturn(List.of(BatchFactory.batchPersisted1With0Quantity()));
        when(purchaseOrderRepository.save(PurchaseOrderFactory.getNewPurchaseOrderConfirmed())).thenReturn(PurchaseOrderFactory.getNewPurchaseOrderConfirmed());
        var obtained = purchaseOrderService.executePurchaseOrder();

        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should throw exception because of cart not existing")
    void executePurchaseOrder2() {
        // arrange

        // act
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.empty());

        // assert

        Assertions.assertThrows(CurrentPurchaseOrderNotExistException.class, ()-> purchaseOrderService.executePurchaseOrder());

    }

    @Test
    @DisplayName("Should return an exception because of product not existing")
    void executePurchaseOrder3() {
        // arrange

        // act
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.of(PurchaseOrderFactory.getPurchaseOrderCreatedWithOneProductPersisted()));
        when(productRepository.findById(ProductFactory.productDTO1().getProductId())).thenReturn(Optional.empty());

        // assert

        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> purchaseOrderService.executePurchaseOrder());
    }

    @Test
    void executePurchaseOrder4() {
        // arrange
        // act
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART)).thenReturn(Optional.of(PurchaseOrderFactory.getPurchaseOrderCreatedWithOneProductPersisted()));
        when(productRepository.findById(ProductFactory.productDTO1().getProductId())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findByProductIdEqualsAndDueDateGreaterThanOrderByDueDateAscending(ProductFactory.productDTO1().getProductId(), LocalDate.now().plus(3, ChronoUnit.WEEKS))).thenReturn(List.of());

        // assert

        Assertions.assertThrows(NotEnoughProductQuantityException.class, ()-> purchaseOrderService.executePurchaseOrder());
    }



    @Test
    @DisplayName("Should return a detail of products in the existent order")
    void getAllProductsInPurchaseOrder() {
        // arrange
        var expected = ProductFactory.getAllProductsInOrder();
        // act
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART))
                .thenReturn(Optional.ofNullable(PurchaseOrderFactory.getPurchaseOrderCreatedWithOneProductPersisted()));
        var obtained = purchaseOrderService.getAllProductsInPurchaseOrder();
        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should throw an exception because of cart not existing")
    void getAllProductsInPurchaseOrder2() {
        // arrange
        // act
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART))
                .thenReturn(Optional.empty());

        // assert

        Assertions.assertThrows(CurrentPurchaseOrderNotExistException.class, () ->purchaseOrderService.getAllProductsInPurchaseOrder());
    }

    @Test
    @DisplayName("Should throw an exception because of lack of products in order")
    void getAllProductsInPurchaseOrder3() {
        // arrange
        // act
        when(purchaseOrderRepository.findByUserIdAndStatusCodeEquals(UserFactory.user1().getId(), PurchaseOrderStatus.CART))
                .thenReturn(Optional.ofNullable(PurchaseOrderFactory.getPurchaseOrderCreated()));

        // assert

        Assertions.assertThrows(NoProductsException.class, () ->purchaseOrderService.getAllProductsInPurchaseOrder());
    }
}
