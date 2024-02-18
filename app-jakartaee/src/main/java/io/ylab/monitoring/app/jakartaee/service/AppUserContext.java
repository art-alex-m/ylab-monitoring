package io.ylab.monitoring.app.jakartaee.service;

import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.model.DomainUser;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.util.Optional;
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
        return Optional.ofNullable(securityContext)
                .map(SecurityContext::getCallerPrincipal)
                .map(Principal::getName)
                .map(UUID::fromString)
                .map(uuid -> (DomainUser) new CoreDomainUser(uuid))
                .orElse(AuditDomainUser.NULL_USER);
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }
}
