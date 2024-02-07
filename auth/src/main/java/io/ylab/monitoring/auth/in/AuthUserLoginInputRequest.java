package io.ylab.monitoring.auth.in;

import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@inheritDoc}
 */
@AllArgsConstructor
@Getter
public class AuthUserLoginInputRequest implements UserLoginInputRequest {
    private final String username;
    private final String password;
}
