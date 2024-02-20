package io.ylab.monitoring.app.springmvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springmvc.config.OpenapiTag;
import io.ylab.monitoring.app.springmvc.in.AppLoginRequest;
import io.ylab.monitoring.app.springmvc.out.AppAuthToken;
import io.ylab.monitoring.app.springmvc.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Вход пользователя в систему
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Tag(name = OpenapiTag.ADMIN)
@Tag(name = OpenapiTag.USER)
@Tag(name = OpenapiTag.AUTH)
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@PermitAll
public class LoginController {

    private final UserLoginInput loginInteractor;

    private final AuthTokenManager tokenManager;

    @POST
    @Operation(summary = "Login users")
    @PostMapping("/login")
    public AppAuthToken login(@Valid @RequestBody AppLoginRequest appLoginRequest) {
        UserLoginInputResponse response = loginInteractor.login(appLoginRequest);

        String token = tokenManager.createToken(response);

        return new AppAuthToken(token);
    }
}
