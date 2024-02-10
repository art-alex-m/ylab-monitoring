package io.ylab.monitoring.domain.core.exception;

/**
 * Исключение "Тип показания счетчика не найден"
 */
public class MeterNotFoundException extends DomainCoreMonitoringException {
    public MeterNotFoundException(String message) {
        super("Meter not found: " + message);
    }
}
