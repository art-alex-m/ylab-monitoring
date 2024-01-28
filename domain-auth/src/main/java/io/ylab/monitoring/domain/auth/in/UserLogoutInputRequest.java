package io.ylab.monitoring.domain.auth.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Запрос выхода из системы
 */
public interface UserLogoutInputRequest {
    DomainUser getUser();
}
