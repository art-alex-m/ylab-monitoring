package io.ylab.monitoring.domain.core.model;

import java.util.UUID;

/**
 * Тип измерения
 */
public interface Meter {
    /**
     * Возвращает идентификатор типа измерения
     *
     * @return UUID
     */
    UUID getId();

    /**
     * Имя типа измерения
     *
     * @return Строка: тепло, газ, электричество и тп
     */
    String getName();
}
