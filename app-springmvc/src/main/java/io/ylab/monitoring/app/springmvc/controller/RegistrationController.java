package io.ylab.monitoring.app.springmvc.controller;

import io.ylab.monitoring.app.springmvc.in.AppRegistrationRequest;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Регистрация пользователя
 */
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    private final UserRegistrationInput registrationInteractor;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@Valid AppRegistrationRequest request) {
        registrationInteractor.register(request);
    }
}
