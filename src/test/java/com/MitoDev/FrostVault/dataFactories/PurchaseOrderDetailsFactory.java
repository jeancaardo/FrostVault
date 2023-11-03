package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.PurchaseOrderTotalPriceResponseDTO;
import com.MitoDev.FrostVault.model.entity.PurchaseOrderDetail;

public class PurchaseOrderDetailsFactory {

    public static PurchaseOrderDetail purchaseNewOrderDetail1(){
        return PurchaseOrderDetail.builder()
                .product(ProductFactory.product1())
                .quantity(50)
                .build();
    }

    public static PurchaseOrderDetail purchaseNewOrderDetail2(){
        return PurchaseOrderDetail.builder()
                .product(ProductFactory.product2())
                .quantity(10)
                .build();
    }

    public static PurchaseOrderDetail purchaseOrderDetailPersisted1(){
        return PurchaseOrderDetail.builder()
                .product(ProductFactory.product1())
                .quantity(50)
                .build();
    }

    public static PurchaseOrderDetail purchaseOrderDetailPersisted2(){
        return PurchaseOrderDetail.builder()
                .product(ProductFactory.product2())
                .quantity(10)
                .build();
    }

}
