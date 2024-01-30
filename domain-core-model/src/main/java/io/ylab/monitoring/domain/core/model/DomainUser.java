package io.ylab.monitoring.domain.core.model;

import java.util.UUID;

/**
 * Информация о пользователе в контексте домена приема показаний счетчиков
 */
public interface DomainUser {

    /**
     * Возвращает идентификатор пользователя
     *
     * @return UUID
     */
    UUID getId();
}
