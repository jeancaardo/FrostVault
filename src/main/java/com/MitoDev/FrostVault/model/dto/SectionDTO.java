package com.MitoDev.FrostVault.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionDTO {

    @NotNull
    @PositiveOrZero
    private Integer sectionCode;

    @NotNull
    @PositiveOrZero
    private Integer warehouseCode;
}
