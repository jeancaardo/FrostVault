package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.dataFactories.ProductFactory;
import com.MitoDev.FrostVault.exception.custom.NoProductsException;
import com.MitoDev.FrostVault.model.dto.ProductListResponseDTO;
import com.MitoDev.FrostVault.model.entity.enums.Type;
import com.MitoDev.FrostVault.repository.IProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    IProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("Should return all products")
    void getAllProducts() {
        // arrange
        var expected = ProductFactory.getAllProductsDTO();
        // act
        when(productRepository.findAll()).thenReturn(ProductFactory.getAllProducts());

        var obtained = productService.getAllProducts();
        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    void getAllProductsByCategory() {
        // arrange
        String category = "FF";
        var expected = new ProductListResponseDTO(List.of(ProductFactory.productReponseDTO1()));
        // act
        when(productRepository.findAllByProductTypeEquals(Type.FROZEN)).thenReturn(List.of(ProductFactory.product1()));

        var obtained = productService.getAllProductsByCategory(category);
        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should throw an exception because there are no products")
    void getAllProductsNoProducts() {
        // arrange
        var expected = ProductFactory.getAllProductsDTO();
        // act
        when(productRepository.findAll()).thenReturn(List.of());

        Assertions.assertThrows(NoProductsException.class, () -> productService.getAllProducts());

    }

    @Test
    @DisplayName("Should throw an exception because there are no products with that category")
    void getAllProductsByCategoryNoProducts() {
        // arrange
        String category = "FF";
        var expected = new ProductListResponseDTO(List.of(ProductFactory.productReponseDTO1()));
        // act
        when(productRepository.findAllByProductTypeEquals(Type.FROZEN)).thenReturn(List.of());
        Assertions.assertThrows(NoProductsException.class, () -> productService.getAllProductsByCategory(category));
    }
}