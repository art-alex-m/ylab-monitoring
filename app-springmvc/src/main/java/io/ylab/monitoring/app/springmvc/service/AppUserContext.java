package io.ylab.monitoring.app.springmvc.service;

import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.domain.core.model.DomainUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Контекст авторизованного пользователя
 *
 * <p>
 *     <a href="https://www.baeldung.com/get-user-in-spring-security">Retrieve User Information in Spring Security</a><br>
 * </p>
 */
@Component
public class AppUserContext {

    public DomainUser getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof DomainUser)
                .map(principal -> (DomainUser) principal)
                .orElse(AuditDomainUser.NULL_USER);
    }
}
