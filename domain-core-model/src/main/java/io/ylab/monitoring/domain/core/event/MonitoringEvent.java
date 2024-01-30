package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.model.DomainUser;

import java.time.Instant;

/**
 * Базовый интерфейс событий предметной области
 */
public interface MonitoringEvent {
    /**
     * Дата время создания события
     *
     * @return Дата время
     */
    Instant getCreatedAt();

    /**
     * Информация о пользователе который инициировал вызов сценария
     *
     * @return Объект
     */
    DomainUser getUser();

    /**
     * Краткое описание события
     *
     * @return Строка
     */
    String getEventName();
}
