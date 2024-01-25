package io.ylab.monitoring.domain.core.event;

/**
 * Обработчик события предметной области
 */
@FunctionalInterface
public interface MonitoringEventHandler {
    boolean handle(MonitoringEvent event);
}
