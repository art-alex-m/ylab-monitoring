package io.ylab.monitoring.auth.in;

import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@inheritDoc}
 */
@AllArgsConstructor
@Getter
public class AuthUserLogoutInputRequest implements UserLogoutInputRequest {
    private final DomainUser user;
}
