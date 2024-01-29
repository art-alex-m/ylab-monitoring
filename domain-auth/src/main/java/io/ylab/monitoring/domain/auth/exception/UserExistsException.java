package io.ylab.monitoring.domain.auth.exception;

/**
 * Пользователь уже существует
 */
public class UserExistsException extends AuthMonitoringException {
    public UserExistsException(String message) {
        super("User already exists " + message);
    }
}
