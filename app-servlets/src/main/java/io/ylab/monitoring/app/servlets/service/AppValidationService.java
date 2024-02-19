package io.ylab.monitoring.app.servlets.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;

/**
 * Валидатор объектов, аннотированных jakarta.validation
 */
public class AppValidationService {

    private final Validator validator;

    public AppValidationService(Validator validator) {
        this.validator = validator;
    }

    public boolean validate(Object subject) throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> violations = validator.validate(subject);
        if (violations.isEmpty()) {
            return true;
        }

        throw new ConstraintViolationException(violations);
    }
}
