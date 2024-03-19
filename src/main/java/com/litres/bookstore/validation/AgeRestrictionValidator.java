package com.litres.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.litres.bookstore.model.AgeRestriction;

public class AgeRestrictionValidator implements ConstraintValidator<ValidAgeRestriction, AgeRestriction> {
    @Override
    public boolean isValid(AgeRestriction value, ConstraintValidatorContext context) {
        return value != null;
    }
}
