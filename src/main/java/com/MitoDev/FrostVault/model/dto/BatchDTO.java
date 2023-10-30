package com.MitoDev.FrostVault.model.dto;

import com.MitoDev.FrostVault.model.dto.validationBeans.Temperatures;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Temperatures
public class BatchDTO {

    private Integer batchNumber;

    @NotNull
    @PositiveOrZero
    private Integer productId;

    @PositiveOrZero
    private Double currentTemperature;

    @PositiveOrZero
    private Double minimumTemperature;

    @PositiveOrZero
    private Integer initialQuantity;

    @PositiveOrZero
    private Integer currentQuantity;

    @PastOrPresent
    private LocalDate manufacturingDate;

    @PastOrPresent
    private LocalDateTime manufacturingTime;

    @Future
    private LocalDate dueDate;


}
