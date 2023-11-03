package com.MitoDev.FrostVault.model.dto;

import com.MitoDev.FrostVault.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsReponseDTO {

    List<ProductDTO> products;
}
