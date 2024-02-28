package io.ylab.monitoring.app.springboot.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "lzYqM4Q/csdx370HTfxKoEddL2uwrzkjux4UQ83k2gVEvRDYU4arK92M6gxhEKgz")
    @JsonProperty("authorization_token")
    private String authorizationToken;
}
