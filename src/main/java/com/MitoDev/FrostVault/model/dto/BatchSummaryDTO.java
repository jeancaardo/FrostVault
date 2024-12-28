package com.MitoDev.FrostVault.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchSummaryDTO {

    Integer batchNumber;

    Integer currentQuantity;

    LocalDate dueDate;
}
