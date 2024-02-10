package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.app.servlets.in.AppLoginRequest;
import io.ylab.monitoring.app.servlets.interceptor.AuditLogger;
import io.ylab.monitoring.app.servlets.interceptor.TimeProfileLog;
import io.ylab.monitoring.app.servlets.out.AppAuthToken;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Вход пользователя в систему
 */
@AuditLogger
@TimeProfileLog
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
@AllArgsConstructor
@NoArgsConstructor
public class LoginResource {

    @Inject
    private UserLoginInput loginInteractor;

    @Inject
    private AuthTokenManager tokenManager;

    @POST
    public AppAuthToken login(@Valid AppLoginRequest appLoginRequest) {
        UserLoginInputResponse response = loginInteractor.login(appLoginRequest);

        String token = tokenManager.createToken(response);

        return new AppAuthToken(token);
    }
}
