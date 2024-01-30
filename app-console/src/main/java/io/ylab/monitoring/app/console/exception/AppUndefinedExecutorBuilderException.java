package io.ylab.monitoring.app.console.exception;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Неизвестный построитель для создания обработчика команды
 */
public class AppUndefinedExecutorBuilderException extends MonitoringException {
    public AppUndefinedExecutorBuilderException(String message) {
        super(message);
    }
}
