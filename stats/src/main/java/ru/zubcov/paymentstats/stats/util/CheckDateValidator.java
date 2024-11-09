package ru.zubcov.paymentstats.stats.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;

import java.time.LocalDateTime;

public class CheckDateValidator implements ConstraintValidator<StartBeforeEndDateValid, ClientRequestStatsDto> {

    @Override
    public void initialize(StartBeforeEndDateValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(ClientRequestStatsDto request, ConstraintValidatorContext constraintValidatorContext) {
        if (request.getEndDate() == null) {
            return true;
        }
        LocalDateTime start = request.getStartDate();
        LocalDateTime end = request.getEndDate();
        if (start == null) {
            return false;
        }
        return start.isBefore(end);
    }
}