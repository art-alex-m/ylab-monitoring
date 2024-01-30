package io.ylab.monitoring.domain.auth.exception;

/**
 * Исключение при создании хешей паролей
 */
public class PasswordEncoderException extends AuthMonitoringException {
    public PasswordEncoderException(String message) {
        super(message);
    }

    public PasswordEncoderException(Throwable throwable) {
        super(throwable);
    }
}
