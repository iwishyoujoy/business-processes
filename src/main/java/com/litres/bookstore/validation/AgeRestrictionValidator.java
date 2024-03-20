package com.litres.bookstore.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.litres.bookstore.model.enums.AgeRestriction;

public class AgeRestrictionValidator implements ConstraintValidator<ValidAgeRestriction, AgeRestriction> {
    @Override
    public boolean isValid(AgeRestriction value, ConstraintValidatorContext context) {
        return value != null;
    }
}
