package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Контекст авторизованного пользователя
 */
public class AppUserContext {

    public DomainUser getCurrentUser() {
        return AuditDomainUser.NULL_USER;
    }
}
