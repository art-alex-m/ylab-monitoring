package io.ylab.monitoring.app.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springboot.config.OpenapiTag;
import io.ylab.monitoring.app.springboot.in.AppRegistrationRequest;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Регистрация пользователя
 */
@Tag(name = OpenapiTag.USER)
@Tag(name = OpenapiTag.AUTH)
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@PermitAll
public class RegistrationController {

    private final UserRegistrationInput registrationInteractor;


    @Operation(summary = "Register new user with USER role", responses = {
            @ApiResponse(responseCode = "204", description = "User registered")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@Valid @RequestBody AppRegistrationRequest request) {
        registrationInteractor.register(request);
    }
}
