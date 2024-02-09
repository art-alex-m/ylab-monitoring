package io.ylab.monitoring.app.servlets.out;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Токен авторизации запроса
 */
@RequiredArgsConstructor
@Getter
public class AppAuthToken {
    @JsonbProperty("authorization_token")
    private final String authorizationToken;
}
