package ru.zubcov.paymentstats.stats.util;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CheckDateValidator.class})
public @interface StartBeforeEndDateValid {
    String message() default "Дата окончания периода не может быть раньше даты начала. " +
            "Или дата началы не может равнятся null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}