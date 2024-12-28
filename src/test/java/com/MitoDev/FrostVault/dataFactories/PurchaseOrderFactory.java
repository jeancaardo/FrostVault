package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.ProductDTO;
import com.MitoDev.FrostVault.model.dto.PurchaseOrderExecutedDTO;
import com.MitoDev.FrostVault.model.dto.PurchaseOrderRequestDTO;
import com.MitoDev.FrostVault.model.dto.PurchaseOrderTotalPriceResponseDTO;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.PurchaseOrder;
import com.MitoDev.FrostVault.model.entity.PurchaseOrderDetail;
import com.MitoDev.FrostVault.model.entity.User;
import com.MitoDev.FrostVault.model.entity.enums.PurchaseOrderStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class PurchaseOrderFactory {

    
    private static final LocalDate DATE = LocalDate.now();

    public static PurchaseOrder createPurchaseOrder(Integer idOrder, User user, PurchaseOrderStatus status, PurchaseOrderDetail... purchaseOrderDetails){
        return PurchaseOrder.builder()
                .idOrder(idOrder)
                .user(user)
                .date(DATE)
                .purchaseOrderDetails(Arrays.asList(purchaseOrderDetails))
                .statusCode(status)
                .build();
    }

    public static PurchaseOrderRequestDTO getNewPurchaseOrderDTO(){
        return PurchaseOrderRequestDTO.builder()
                .buyerId(1)
                .date(DATE.minus(1, ChronoUnit.MONTHS))
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
                .totalPrice(50000D)
                .build();
    }

    public static PurchaseOrderExecutedDTO purchaseOrderExecutedDTO(){
        return PurchaseOrderExecutedDTO.builder()
                .amountExecuted(25000D)
                .cardNumber("1234 5678")
                .build();
    }
}
