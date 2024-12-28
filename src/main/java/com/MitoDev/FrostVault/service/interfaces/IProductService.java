package com.MitoDev.FrostVault.service.interfaces;


import com.MitoDev.FrostVault.model.dto.ProductListResponseDTO;

public interface IProductService {

    ProductListResponseDTO getAllProducts();

    ProductListResponseDTO getAllProductsByCategory(String category);
}
