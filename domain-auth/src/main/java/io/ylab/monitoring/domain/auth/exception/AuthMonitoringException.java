package io.ylab.monitoring.domain.auth.exception;

import io.ylab.monitoring.domain.core.exception.BusinessLogicMonitoringException;

/**
 * Базовое исключение поддомена аудита
 */
public abstract class AuthMonitoringException extends BusinessLogicMonitoringException {
    AuthMonitoringException(String message) {
        super(message);
    }

    AuthMonitoringException(Throwable throwable) {
        super(throwable);
    }
}
