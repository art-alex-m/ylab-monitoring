package io.ylab.monitoring.app.springboot.controller;

import io.ylab.monitoring.app.springboot.out.AppError;
import io.ylab.monitoring.domain.core.exception.BusinessLogicMonitoringException;
import io.ylab.monitoring.domain.core.exception.MonitoringException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleValidationException(MethodArgumentNotValidException ex) {
        FieldError error = (FieldError) ex.getBindingResult().getAllErrors().get(0);
        return new AppError(ex.getClass().getName(), error.getField(), error.getDefaultMessage());
    }

    @ExceptionHandler(BusinessLogicMonitoringException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleValidationException(MonitoringException ex) {
        return handleAllErrors(ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppError handleSecurityException(AccessDeniedException ex) {
        return handleAllErrors(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError handleAllErrors(Exception ex) {
        return new AppError(ex.getClass().getName(), "message", ex.getMessage());
    }
}
