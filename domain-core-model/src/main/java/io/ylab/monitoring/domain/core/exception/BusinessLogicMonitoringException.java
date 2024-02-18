package io.ylab.monitoring.domain.core.exception;

/**
 * Базовый класс исключений для бизнес-логики
 */
public abstract class BusinessLogicMonitoringException extends MonitoringException {
    public BusinessLogicMonitoringException(String message) {
        super(message);
    }

    public BusinessLogicMonitoringException(Throwable throwable) {
        super(throwable);
    }
}
