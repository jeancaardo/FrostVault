package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.ProductDTO;
import com.MitoDev.FrostVault.model.dto.ProductListResponseDTO;
import com.MitoDev.FrostVault.model.dto.ProductReponseDTO;
import com.MitoDev.FrostVault.model.dto.ProductsReponseDTO;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.enums.Type;

import java.util.List;

public class ProductFactory {

    public static Product product1(){
        return Product.builder()
                .idProduct(1)
                .name("Tomatoes")
                .price(500)
                .productType(Type.FRESH)
                .temperature(20D)
                .build();
    }

    public static Product product2(){
        return Product.builder()
                .idProduct(2)
                .name("Ice Cream")
                .price(500)
                .productType(Type.FROZEN)
                .temperature(-12D)
                .build();
    }

    public static ProductDTO productDTO1(){
        return ProductDTO.builder()
                .productId(1)
                .quantity(50)
                .build();
    }

    public static ProductDTO productDTO2(){
        return ProductDTO.builder()
                .productId(2)
                .quantity(500)
                .build();
    }

    public static List<Product> getAllProducts(){
        return List.of(product1(), product2());
    }

    public static ProductReponseDTO productReponseDTO(){
        return ProductReponseDTO.builder()
                .idProduct(1)
                .name("Tomatoes")
                .price(500)
                .productType(Type.FRESH)
                .temperature(20D)
                .build();
    }

    public static ProductReponseDTO productReponseDTO2(){
        return ProductReponseDTO.builder()
                .idProduct(2)
                .name("Ice Cream")
                .price(500)
                .productType(Type.FROZEN)
                .temperature(-12D)
                .build();
    }

    public static ProductListResponseDTO getAllProductsDTO(){
        return ProductListResponseDTO.builder()
                .products(List.of(productReponseDTO(), productReponseDTO2()))
                .build();
    }

    public static ProductsReponseDTO getAllProductsInOrder(){
        return new ProductsReponseDTO(List.of(productDTO1()));
    }
}
