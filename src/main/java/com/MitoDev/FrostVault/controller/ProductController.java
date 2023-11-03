package com.MitoDev.FrostVault.controller;

import com.MitoDev.FrostVault.model.dto.ProductListResponseDTO;
import com.MitoDev.FrostVault.service.interfaces.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api/v1/fresh-products/")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public ProductListResponseDTO getAllProducts(@RequestParam(value = "category", required = false) String cat){
        return cat == null
                ? productService.getAllProducts()
                : productService.getAllProductsByCategory(cat);
    }
}
