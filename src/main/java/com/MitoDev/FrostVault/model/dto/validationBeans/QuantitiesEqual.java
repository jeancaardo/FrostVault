package com.MitoDev.FrostVault.model.dto.validationBeans;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = TemperatureValid.class)
@Documented
public @interface QuantitiesEqual {
    String message() default "Quantities cannot be different on saving";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
