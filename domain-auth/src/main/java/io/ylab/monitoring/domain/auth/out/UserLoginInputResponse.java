package io.ylab.monitoring.domain.auth.out;

import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Ответ сценария "Идентификация пользователя в системе"
 */
public interface UserLoginInputResponse extends DomainUser {
    DomainRole getRole();
}
