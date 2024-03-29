package com.litres.bookstore.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeRestrictionValidator.class)
public @interface ValidAgeRestriction {
    String message() default "Invalid age restriction";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
