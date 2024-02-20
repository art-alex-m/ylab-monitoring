package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.app.servlets.component.HttpRequestAttribute;
import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.domain.core.model.DomainUser;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * Контекст авторизованного пользователя
 */
public class AppUserContext {

    public DomainUser getCurrentUser(HttpServletRequest request) {
        return Optional.ofNullable(request.getAttribute(HttpRequestAttribute.PRINCIPAL))
                .map(principal -> (DomainUser) principal)
                .orElse(AuditDomainUser.NULL_USER);
    }
}
