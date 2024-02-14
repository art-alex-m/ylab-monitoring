package io.ylab.monitoring.app.springmvc.controller;

import io.ylab.monitoring.app.springmvc.in.AppLoginRequest;
import io.ylab.monitoring.app.springmvc.out.AppAuthToken;
import io.ylab.monitoring.app.springmvc.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Вход пользователя в систему
 */
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@PermitAll
public class LoginController {

    private final UserLoginInput loginInteractor;

    private final AuthTokenManager tokenManager;

    @PostMapping("/login")
    public AppAuthToken login(@Valid @RequestBody AppLoginRequest appLoginRequest) {
        UserLoginInputResponse response = loginInteractor.login(appLoginRequest);

        String token = tokenManager.createToken(response);

        return new AppAuthToken(token);
    }
}
