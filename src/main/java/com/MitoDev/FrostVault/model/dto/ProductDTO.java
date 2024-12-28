package com.MitoDev.FrostVault.model.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @PositiveOrZero
    Integer productId;

    @PositiveOrZero
    Integer quantity;
}
