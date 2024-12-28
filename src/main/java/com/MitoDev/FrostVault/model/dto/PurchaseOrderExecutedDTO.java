package com.MitoDev.FrostVault.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderExecutedDTO {

    private Double amountExecuted;

    private String cardNumber;

}
