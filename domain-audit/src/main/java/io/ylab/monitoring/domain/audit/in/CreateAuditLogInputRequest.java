package io.ylab.monitoring.domain.audit.in;

import io.ylab.monitoring.domain.core.model.DomainUserable;

import java.time.Instant;

/**
 * Запрос в сценарии "Запись лога действий пользователей"
 */
public interface CreateAuditLogInputRequest extends DomainUserable {
    /**
     * Время возникновения события аудита
     *
     * @return Instant
     */
    Instant getOccurredAt();

    /**
     * Имя события аудита
     *
     * @return строка
     */
    String getName();
}
