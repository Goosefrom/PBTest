package com.goose.pbtest.config.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = NumberConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberConstraint {

    String message() default "Must be only numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
