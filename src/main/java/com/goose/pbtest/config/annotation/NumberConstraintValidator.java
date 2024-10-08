package com.goose.pbtest.config.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberConstraintValidator implements ConstraintValidator<NumberConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) return false;

         return value.chars().allMatch(Character::isDigit);
    }
}
