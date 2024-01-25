package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.exception.UserExistsException;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;

public interface UserRegistrationInput {
    boolean register(UserRegistrationInputRequest request) throws UserExistsException;
}
