package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppLoginRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AppValidationServiceTest {

    AppValidationService sut;

    @BeforeEach
    void setUp() {
        sut = new AppValidationService(AppConfiguration.REGISTRY.hibernateValidator());
    }

    @Test
    void givenSubject_whenValidate_thenSuccess() {
        AppLoginRequest request = new AppLoginRequest("username", "password");

        boolean result = sut.validate(request);

        assertThat(result).isTrue();
    }

    @Test
    void givenWrongSubject_whenValidate_thenViolationException() {
        AppLoginRequest request = new AppLoginRequest("", "");

        Throwable result = catchThrowable(() -> sut.validate(request));

        assertThat(result).isInstanceOf(ConstraintViolationException.class);
        assertThat(((ConstraintViolationException) result).getConstraintViolations()).hasSize(1);
    }
}