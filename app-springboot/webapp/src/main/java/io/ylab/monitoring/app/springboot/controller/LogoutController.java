package io.ylab.monitoring.app.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springboot.config.OpenapiTag;
import io.ylab.monitoring.app.springboot.service.AuthTokenManager;
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
@Tag(name = OpenapiTag.ADMIN)
@Tag(name = OpenapiTag.USER)
@Tag(name = OpenapiTag.AUTH)
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@PermitAll
public class LogoutController {

    private final AuthTokenManager tokenManager;


    @Operation(summary = "Logout", parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    required = false,
                    name = "Authorization",
                    schema = @Schema(implementation = String.class)
            )},
            responses = {
                    @ApiResponse(responseCode = "204", description = "User logout")
            }
    )
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(value = "Authorization", required = false) String authToken) {
        if (authToken != null) {
            tokenManager.revokeToken(authToken);
        }
    }
}
