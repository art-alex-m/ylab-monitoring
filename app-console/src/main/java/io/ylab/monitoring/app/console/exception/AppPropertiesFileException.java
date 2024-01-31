package io.ylab.monitoring.app.console.exception;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Ошибка загрузки файла настроек консольного приложения
 */
public class AppPropertiesFileException extends MonitoringException {
    public AppPropertiesFileException(String message) {
        super(message);
    }

    public AppPropertiesFileException(Throwable throwable) {
        super(throwable);
    }
}
