package com.MitoDev.FrostVault.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCountByWarehouseDTO {

    private Integer warehouseId;

    private Integer productCount;

}
