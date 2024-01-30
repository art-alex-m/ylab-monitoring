package io.ylab.monitoring.app.console.exception;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Неизвестный обработчик команды
 */
public class AppUndefinedCommandExecutorException extends MonitoringException {
    public AppUndefinedCommandExecutorException(String message) {
        super(message);
    }
}
