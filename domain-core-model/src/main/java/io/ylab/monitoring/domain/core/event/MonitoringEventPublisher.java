package io.ylab.monitoring.domain.core.event;

/**
 * Публикует события предметной области
 */
public interface MonitoringEventPublisher {
    /**
     * Публикует событие для всех подписчиков
     *
     * @param event Доменное событие
     * @return Истина
     */
    boolean publish(MonitoringEvent event);
}
