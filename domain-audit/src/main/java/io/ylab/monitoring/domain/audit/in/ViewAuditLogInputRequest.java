package io.ylab.monitoring.domain.audit.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Запрос в сценарии "Просмотр лога действий"
 */
public interface ViewAuditLogInputRequest {
    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();
}
