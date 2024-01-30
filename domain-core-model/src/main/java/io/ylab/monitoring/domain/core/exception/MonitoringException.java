package io.ylab.monitoring.domain.core.exception;

/**
 * Базовый класс для исключений предметной области
 */
public abstract class MonitoringException extends RuntimeException {
    public MonitoringException(String message) {
        super(message);
    }

    public MonitoringException(Throwable throwable) {
        super(throwable);
    }
}
