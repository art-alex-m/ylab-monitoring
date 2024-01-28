package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppUserRegistrationInputRequest implements UserRegistrationInputRequest {
    private final String username;
    private final String password;
}
