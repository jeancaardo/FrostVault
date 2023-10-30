package com.MitoDev.FrostVault.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrderRequestDTO {

    private Integer orderNumber;

    private LocalDate orderDate;

    private @Valid SectionDTO section;

    private List<@Valid BatchDTO> batchStock;
}
