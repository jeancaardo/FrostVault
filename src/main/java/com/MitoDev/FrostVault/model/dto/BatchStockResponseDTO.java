package com.MitoDev.FrostVault.model.dto;

import com.MitoDev.FrostVault.model.entity.Section;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchStockResponseDTO {

    Integer productId;

    List<BatchBySectionDTO> batchs;

}
