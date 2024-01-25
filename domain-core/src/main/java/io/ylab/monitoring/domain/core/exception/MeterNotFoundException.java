package io.ylab.monitoring.domain.core.exception;

/**
 * Исключение "Тип показания счетчика не найден"
 */
public class MeterNotFoundException extends MonitoringException {
    public MeterNotFoundException(String message) {
        super(message);
    }
}
