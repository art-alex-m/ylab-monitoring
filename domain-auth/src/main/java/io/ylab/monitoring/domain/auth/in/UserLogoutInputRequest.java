package io.ylab.monitoring.domain.auth.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Запрос выхода из системы
 */
public interface UserLogoutInputRequest {
    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();
}
