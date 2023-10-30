package com.MitoDev.FrostVault.model.dto.validationBeans;

import com.MitoDev.FrostVault.model.dto.BatchDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TemperatureValid implements ConstraintValidator<Temperatures, BatchDTO> {

    @Override
    public void initialize(Temperatures constraintAnnotation) {
    }

    @Override
    public boolean isValid(BatchDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getCurrentTemperature() < dto.getMinimumTemperature();
    }
}