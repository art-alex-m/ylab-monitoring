package io.ylab.monitoring.app.springmvc.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Токен авторизации запроса
 */
@RequiredArgsConstructor
@Getter
public class AppAuthToken {
    @JsonProperty("authorization_token")
    private final String authorizationToken;
}
