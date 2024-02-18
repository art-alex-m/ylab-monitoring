package io.ylab.monitoring.app.servlets.component;

import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/api/*")
public class BearerAuthorizationFilter extends HttpFilter {

    public static final String PARAM_AUTHORIZATION = "Authorization";

    private final AuthTokenManager tokenManager;

    private ServletContext context;

    public BearerAuthorizationFilter() {
        this.tokenManager = AppConfiguration.REGISTRY.authTokenManager();
    }

    @Override
    protected void doFilter(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain)
            throws IOException, ServletException {

        String authHeader = httpRequest.getHeader(PARAM_AUTHORIZATION);
        UserLoginInputResponse response = tokenManager.findByKey(authHeader);

        if (response != null) {
            Set<String> roles = Set.of(response.getRole().name());
            httpRequest.authenticate(httpResponse);
        }

        chain.doFilter(httpRequest, httpResponse);
    }
}
