package io.ylab.monitoring.domain.core.event;

/**
 * Публикует события предметной области
 */
public interface MonitoringEventPublisher {
    boolean publish(MonitoringEvent event);
}
