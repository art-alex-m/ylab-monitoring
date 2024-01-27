package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppUserLoginInputRequest implements UserLoginInputRequest {
    private final String username;
    private final String password;
}
