package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppUserLogoutInputRequest implements UserLogoutInputRequest {
    private final DomainUser user;
}
