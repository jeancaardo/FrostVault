package com.MitoDev.FrostVault.model.dto;

import com.MitoDev.FrostVault.model.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchNearExpirationDTO {

    private Integer batchNumber;

    private Integer productId;

    private Type productTypeId;

    private Integer currentQuantity;

}
