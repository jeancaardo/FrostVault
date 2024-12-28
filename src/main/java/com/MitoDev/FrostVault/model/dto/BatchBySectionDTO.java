package com.MitoDev.FrostVault.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchBySectionDTO {

    SectionDTO section;

    List<BatchSummaryDTO> batchesPerSection;
}
