package io.ylab.monitoring.domain.core.event;

/**
 * Обработчик события предметной области
 */
@FunctionalInterface
public interface MonitoringEventHandler {
    /**
     * Обрабатывает событие
     *
     * @param event Событие ядра
     * @return Истина
     */
    boolean handle(MonitoringEvent event);
}
