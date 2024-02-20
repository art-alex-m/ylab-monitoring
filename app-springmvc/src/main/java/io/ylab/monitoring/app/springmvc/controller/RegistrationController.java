package io.ylab.monitoring.app.springmvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springmvc.config.OpenapiTag;
import io.ylab.monitoring.app.springmvc.in.AppRegistrationRequest;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
@Path("/register")
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Tag(name = OpenapiTag.USER)
@Tag(name = OpenapiTag.AUTH)
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@PermitAll
public class RegistrationController {

    private final UserRegistrationInput registrationInteractor;

    @POST
    @Operation(summary = "Register new user with USER role", responses = {
            @ApiResponse(responseCode = "204",
                    description = "User registered",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@Valid @RequestBody AppRegistrationRequest request) {
        registrationInteractor.register(request);
    }
}
