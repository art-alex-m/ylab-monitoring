package io.ylab.monitoring.domain.core.model;

/**
 * Контекст текущего пользователя при выполнении запроса
 */
public interface UserContext {

    DomainUser getCurrentUser();
}
