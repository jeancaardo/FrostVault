package com.MitoDev.FrostVault.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDTO {

    private List<BatchDTO> batchStock;
}
