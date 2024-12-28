package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.exception.custom.NoProductsException;
import com.MitoDev.FrostVault.model.dto.ProductListResponseDTO;
import com.MitoDev.FrostVault.model.dto.ProductReponseDTO;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.enums.Type;
import com.MitoDev.FrostVault.repository.IProductRepository;
import com.MitoDev.FrostVault.service.interfaces.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductListResponseDTO getAllProducts() {

        var list = productRepository.findAll();

        if (list.isEmpty())
            throw new NoProductsException();

        return new ProductListResponseDTO(
                list.stream().map(
                        this::fromEntityToDto
                ).toList());
    }

    @Override
    public ProductListResponseDTO getAllProductsByCategory(String category) {
        Type type;
        switch (category){
            case "FS":
                type = Type.FRESH;
                break;
            case "FF" :
                type = Type.FROZEN;
                break;
            case "RF":
                type = Type.COOLED;
                break;
            default:
                type = Type.REGULAR;
        }
        var list = productRepository.findAllByProductTypeEquals(type);

        if (list.isEmpty())
            throw new NoProductsException(type.toString());

        return new ProductListResponseDTO(
                list.stream().map(
                        this::fromEntityToDto
                ).toList());
    }

    private ProductReponseDTO fromEntityToDto(Product p){
        return ProductReponseDTO.builder()
                                .idProduct(p.getIdProduct())
                                .name(p.getName())
                                .productType(p.getProductType())
                                .price(p.getPrice())
                                .temperature(p.getTemperature())
                                .build();
    }
}
