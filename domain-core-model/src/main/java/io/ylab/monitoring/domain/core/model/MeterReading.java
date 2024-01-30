package io.ylab.monitoring.domain.core.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Показание счетчика
 */
public interface MeterReading {
    /**
     * Возвращает идентификатор показания счетчика
     *
     * @return UUID
     */
    UUID getId();

    /**
     * Возвращает пользователя подавшего показания
     *
     * @return Объект пользователя
     */
    DomainUser getUser();

    /**
     * Возвращает период за который передано показание счетчика
     *
     * @return Время и дата
     */
    Instant getPeriod();

    /**
     * Возвращает тип измерения
     *
     * @return Объект
     */
    Meter getMeter();

    /**
     * Показание прибора учета
     *
     * @return Целочисленное показание
     */
    long getValue();

    /**
     * Дата создания показания
     *
     * @return Объект дата время
     */
    Instant getCreatedAt();
}
