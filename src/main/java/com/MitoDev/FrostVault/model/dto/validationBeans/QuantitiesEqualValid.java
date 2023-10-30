package com.MitoDev.FrostVault.model.dto.validationBeans;

import com.MitoDev.FrostVault.model.dto.BatchDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class QuantitiesEqualValid implements ConstraintValidator<QuantitiesEqual, BatchDTO>{

    @Override
    public void initialize(QuantitiesEqual constraintAnnotation) {

    }

    @Override
    public boolean isValid(BatchDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(dto.getInitialQuantity(), dto.getCurrentQuantity());
    }
}
