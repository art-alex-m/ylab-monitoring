package io.ylab.monitoring.domain.auth.exception;

public class UserExistsException extends AuthMonitoringException {
    public UserExistsException(String message) {
        super(message);
    }
}
