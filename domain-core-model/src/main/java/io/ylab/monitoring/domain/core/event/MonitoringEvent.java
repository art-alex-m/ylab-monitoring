package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.model.DomainUserable;

import java.time.Instant;

/**
 * Базовый интерфейс событий предметной области
 */
public interface MonitoringEvent extends DomainUserable {
    /**
     * Дата время создания события
     *
     * @return Дата время
     */
    Instant getCreatedAt();

    /**
     * Краткое описание события
     *
     * @return Строка
     */
    String getEventName();
}
