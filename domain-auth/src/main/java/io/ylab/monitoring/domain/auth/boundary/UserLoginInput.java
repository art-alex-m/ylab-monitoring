package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.exception.UserNotFoundException;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;

public interface UserLoginInput {
    boolean login(UserLoginInputRequest request) throws UserNotFoundException;
}
