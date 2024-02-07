package io.ylab.monitoring.auth.in;

import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@inheritDoc}
 */
@AllArgsConstructor
@Getter
public class AuthUserRegistrationInputRequest implements UserRegistrationInputRequest {
    private final String username;
    private final String password;
}
