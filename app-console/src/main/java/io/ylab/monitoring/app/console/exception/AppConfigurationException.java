package io.ylab.monitoring.app.console.exception;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Ошибка при конфигурировании приложения
 */
public class AppConfigurationException extends MonitoringException {
    public AppConfigurationException(String message) {
        super(message);
    }

    public AppConfigurationException(Throwable throwable) {
        super(throwable);
    }
}
