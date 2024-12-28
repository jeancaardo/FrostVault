package com.MitoDev.FrostVault.model.dto;

import com.MitoDev.FrostVault.model.entity.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReponseDTO {

    private Integer idProduct;

    private String name;

    private Integer price;

    private Double temperature;

    private Type productType;

}
