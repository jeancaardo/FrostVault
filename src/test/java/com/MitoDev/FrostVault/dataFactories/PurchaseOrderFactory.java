package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.ProductDTO;
import com.MitoDev.FrostVault.model.dto.PurchaseOrderExecutedDTO;
import com.MitoDev.FrostVault.model.dto.PurchaseOrderRequestDTO;
import com.MitoDev.FrostVault.model.dto.PurchaseOrderTotalPriceResponseDTO;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.PurchaseOrder;
import com.MitoDev.FrostVault.model.entity.enums.PurchaseOrderStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PurchaseOrderFactory {

    public static PurchaseOrder getNewPurchaseOrder(){
        return PurchaseOrder.builder()
                .user(UserFactory.user1())
                .date(LocalDate.now())
                .purchaseOrderDetails(List.of())
                .statusCode(PurchaseOrderStatus.CART)
                .build();
    }

    public static PurchaseOrder getPurchaseOrderCreated(){
        return PurchaseOrder.builder()
                .idOrder(1)
                .user(UserFactory.user1())
                .date(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                .purchaseOrderDetails(List.of())
                .statusCode(PurchaseOrderStatus.CART)
                .build();
    }

    public static PurchaseOrder getPurchaseOrderCreatedAddingOneProduct(){
        return PurchaseOrder.builder()
                .idOrder(1)
                .user(UserFactory.user1())
                .date(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                .purchaseOrderDetails(List.of(PurchaseOrderDetailsFactory.purchaseNewOrderDetail1()))
                .statusCode(PurchaseOrderStatus.CART)
                .build();
    }

    public static PurchaseOrder getPurchaseOrderCreatedWithOneProductPersisted(){
        return PurchaseOrder.builder()
                .idOrder(1)
                .user(UserFactory.user1())
                .date(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                .purchaseOrderDetails(List.of(PurchaseOrderDetailsFactory.purchaseOrderDetailPersisted1()))
                .statusCode(PurchaseOrderStatus.CART)
                .build();
    }


    public static PurchaseOrder getNewPurchaseOrderConfirmed(){
        return PurchaseOrder.builder()
                .idOrder(1)
                .user(UserFactory.user1())
                .date(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                .purchaseOrderDetails(List.of(PurchaseOrderDetailsFactory.purchaseNewOrderDetail1()))
                .statusCode(PurchaseOrderStatus.CONFIRMED)
                .build();
    }

    public static PurchaseOrderRequestDTO getNewPurchaseOrderDTO(){
        return PurchaseOrderRequestDTO.builder()
                .buyerId(1)
                .date(LocalDate.now().minus(1, ChronoUnit.MONTHS))
                .products(List.of(ProductFactory.productDTO1()))
                .statusCode("Cart")
                .build();
    }

    public static PurchaseOrderTotalPriceResponseDTO totalOfBrandNewCart(){
        return PurchaseOrderTotalPriceResponseDTO.builder()
                .totalPrice(0D)
                .build();
    }

    public static PurchaseOrderTotalPriceResponseDTO totalOfAddingProduct1(){
        return PurchaseOrderTotalPriceResponseDTO.builder()
                .totalPrice(25000D)
                .build();
    }

    public static PurchaseOrderExecutedDTO purchaseOrderExecutedDTO(){
        return PurchaseOrderExecutedDTO.builder()
                .amountExecuted(25000D)
                .cardNumber("1234 5678")
                .build();
    }
}
