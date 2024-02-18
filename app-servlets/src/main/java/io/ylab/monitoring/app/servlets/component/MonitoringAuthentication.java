package io.ylab.monitoring.app.servlets.component;

import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.Set;

/**
 * Авторизация пользователя
 *
 * <p>
 *     <a href="https://www.baeldung.com/java-ee-8-security">Jakarta EE 8 Security API</a><br>
 *     <a href="https://tomee.apache.org/latest/examples/security-tomcat-user-identitystore.html">Jakarta Security with Tomcat tomcat-users.xml identity store</a><br>
 *     <a href="https://tomee.apache.org/master/examples/security-custom-identitystore.html">Jakarta Security with a custom identity store</a><br>
 *     <a href="https://stackoverflow.com/questions/62972230/cannot-inject-javax-security-enterprise-securitycontext-in-basic-jee8-login-appl">Cannot inject javax.security.enterprise.SecurityContext in basic JEE8 login application</a><br>
 * </p>
 */
@ApplicationScoped
public class MonitoringAuthentication implements HttpAuthenticationMechanism {

    public static final String PARAM_AUTHORIZATION = "Authorization";

    @Inject
    private AuthTokenManager tokenManager;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpRequest,
            HttpServletResponse httpResponse, HttpMessageContext httpMessageContext)
            throws AuthenticationException {

        UserLoginInputResponse response = tokenManager.findByKey(httpRequest.getHeader(PARAM_AUTHORIZATION));

        if (response == null) {
            return httpMessageContext.doNothing();
        }

        Set<String> roles = new HashSet<>();
        roles.add(response.getRole().name());

        return httpMessageContext.notifyContainerAboutLogin(response.getId().toString(), roles);
    }
}
