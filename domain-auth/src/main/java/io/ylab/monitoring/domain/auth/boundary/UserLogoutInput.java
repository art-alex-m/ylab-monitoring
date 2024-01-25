package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;

public interface UserLogoutInput {
    boolean logout(UserLogoutInputRequest request);
}
