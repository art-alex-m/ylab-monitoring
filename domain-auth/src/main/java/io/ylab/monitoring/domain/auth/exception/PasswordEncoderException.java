package io.ylab.monitoring.domain.auth.exception;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Исключение при создании хешей паролей
 */
public class PasswordEncoderException extends MonitoringException {
    public PasswordEncoderException(String message) {
        super(message);
    }

    public PasswordEncoderException(Throwable throwable) {
        super(throwable);
    }
}
