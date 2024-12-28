package com.MitoDev.FrostVault.model.dto;

import com.MitoDev.FrostVault.model.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderRequestDTO {

    @PositiveOrZero
    Integer purchaseOrderId;

    @PastOrPresent
    LocalDate date;

    @PositiveOrZero
    Integer buyerId;

    String statusCode;

    List<@Valid ProductDTO> products;

}
