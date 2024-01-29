package io.ylab.monitoring.domain.auth.exception;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Базовое исключение поддомена аудита
 */
public abstract class AuthMonitoringException extends MonitoringException {
    AuthMonitoringException(String message) {
        super(message);
    }
}
