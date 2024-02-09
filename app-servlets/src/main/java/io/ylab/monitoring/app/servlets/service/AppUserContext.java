package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.model.DomainUser;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Контекст авторизованного пользователя
 */
@RequestScoped
@AllArgsConstructor
@NoArgsConstructor
public class AppUserContext {

    @Inject
    private SecurityContext securityContext;

    public DomainUser getCurrentUser() {
        UUID id = UUID.fromString(securityContext.getCallerPrincipal().getName());
        return new CoreDomainUser(id);
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }
}
