package io.ylab.monitoring.app.springmvc.controller;

import io.ylab.monitoring.app.springmvc.service.AuthTokenManager;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Выход пользователя из системы
 */
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@PermitAll
public class LogoutController {

    private final AuthTokenManager tokenManager;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(value = "Authorization", required = false) String authToken) {
        if (authToken != null) {
            tokenManager.revokeToken(authToken);
        }
    }
}
