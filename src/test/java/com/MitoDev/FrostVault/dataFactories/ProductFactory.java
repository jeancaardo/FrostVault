package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.enums.Type;

public class ProductFactory {

    public static Product product1(){
        return Product.builder()
                .idProduct(1)
                .name("Tomatoes")
                .price(500)
                .productType(Type.FRESH)
                .temperature(20D)
                .totalQuantity(500)
                .build();
    }

    public static Product product2(){
        return Product.builder()
                .idProduct(2)
                .name("Ice Cream")
                .price(500)
                .productType(Type.FROZEN)
                .temperature(-12D)
                .totalQuantity(100)
                .build();
    }
}
