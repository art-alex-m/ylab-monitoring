package io.ylab.monitoring.app.servlets.component;

import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.exception.AccessDeniedServletException;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import io.ylab.monitoring.domain.core.model.DomainRole;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Авторизация запросов по токену
 */
@WebFilter("/api/*")
public class BearerAuthorizationFilter extends HttpFilter {

    public static final String PARAM_AUTHORIZATION = "Authorization";

    private final AuthTokenManager tokenManager;

    public BearerAuthorizationFilter() {
        this.tokenManager = AppConfiguration.REGISTRY.authTokenManager();
    }

    @Override
    protected void doFilter(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain)
            throws IOException, ServletException {

        String authHeader = httpRequest.getHeader(PARAM_AUTHORIZATION);
        UserLoginInputResponse principal = tokenManager.findByKey(authHeader);

        if (!isPermitted(httpRequest, principal)) {
            throw new AccessDeniedServletException("Access Denied");
        }

        if (principal != null) {
            httpRequest.setAttribute(HttpRequestAttribute.PRINCIPAL, principal);
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private boolean isPermitted(HttpServletRequest httpRequest, UserLoginInputResponse principal) {

        String servletName = httpRequest.getHttpServletMapping().getServletName();
        String servletClassName = httpRequest.getServletContext().getServletRegistration(servletName).getClassName();
        Class<?> servletClass = null;

        try {
            servletClass = Class.forName(servletClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Servlet class not found: " + servletClassName, e);
        }

        if (servletClass.getAnnotation(DenyAll.class) != null) {
            return false;
        }

        if (servletClass.getAnnotation(PermitAll.class) != null) {
            return true;
        }

        String currentRole = Optional.ofNullable(principal)
                .map(UserLoginInputResponse::getRole)
                .map(Enum::name)
                .orElse(DomainRole.ANONYMOUS.name());

        boolean permitted = Optional.ofNullable(servletClass.getAnnotation(RolesAllowed.class))
                .map(RolesAllowed::value)
                .map(roles -> Arrays.stream(roles).collect(Collectors.toSet()))
                .map(roles -> roles.contains(currentRole))
                .orElse(false);

        return permitted;
    }
}
