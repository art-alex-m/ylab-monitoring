package io.ylab.monitoring.app.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springboot.config.OpenapiTag;
import io.ylab.monitoring.app.springboot.in.AppLoginRequest;
import io.ylab.monitoring.app.springboot.out.AppAuthToken;
import io.ylab.monitoring.app.springboot.service.AuthTokenManager;
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

    @Operation(summary = "Login users", responses = {
            @ApiResponse(responseCode = "200", description = "User login",
                    content = @Content(schema = @Schema(implementation = AppAuthToken.class)))
    })
    @PostMapping("/login")
    public AppAuthToken login(@Valid @RequestBody AppLoginRequest appLoginRequest) {
        UserLoginInputResponse response = loginInteractor.login(appLoginRequest);

        String token = tokenManager.createToken(response);

        return new AppAuthToken(token);
    }
}
