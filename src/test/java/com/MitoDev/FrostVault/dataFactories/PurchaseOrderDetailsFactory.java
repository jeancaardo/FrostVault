package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.PurchaseOrderTotalPriceResponseDTO;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.PurchaseOrderDetail;

public class PurchaseOrderDetailsFactory {

    public static PurchaseOrderDetail createOrderDetail(Integer idOrderDetail, Product product){
        return PurchaseOrderDetail.builder()
                .idOrderDetail(idOrderDetail)
                .product(product)
                .quantity(50)
                .build();
    }

}
