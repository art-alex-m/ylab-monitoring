package io.ylab.monitoring.domain.audit.model;

import io.ylab.monitoring.domain.core.model.DomainUser;

import java.time.Instant;

/**
 * Запись лога аудита
 */
public interface AuditItem {
    /**
     * Время возникновения события аудита
     *
     * @return Instant
     */
    Instant getOccurredAt();

    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();

    /**
     * Имя события аудита
     *
     * @return строка
     */
    String getName();
}
