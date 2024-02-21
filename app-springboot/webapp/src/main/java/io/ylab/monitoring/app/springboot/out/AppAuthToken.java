package io.ylab.monitoring.app.springboot.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Токен авторизации запроса
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppAuthToken {
    @JsonProperty("authorization_token")
    private String authorizationToken;
}
