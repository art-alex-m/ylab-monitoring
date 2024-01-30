package io.ylab.monitoring.domain.core.exception;

/**
 * Исключение "Такие показания счетчика уже существуют"
 */
public class MeterReadingExistsException extends MonitoringException {
    public MeterReadingExistsException(String message) {
        super("Meter reading already exists: " + message);
    }
}
