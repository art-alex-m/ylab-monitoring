package io.ylab.monitoring.domain.auth.exception;

public class UserNotFoundException extends AuthMonitoringException {
    public UserNotFoundException(String message) {
        super("User not found " + message);
    }
}
