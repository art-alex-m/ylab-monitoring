package io.ylab.monitoring.domain.core.exception;

/**
 * Доменное исключение для подсистемы событий
 */
public class MonitoringEventPublisherException extends MonitoringException {
    public MonitoringEventPublisherException(String message) {
        super(message);
    }
}
