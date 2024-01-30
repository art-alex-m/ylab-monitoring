package io.ylab.monitoring.domain.audit.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

import java.time.Instant;

/**
 * Запрос в сценарии "Запись лога действий пользователей"
 */
public interface CreateAuditLogInputRequest {
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
