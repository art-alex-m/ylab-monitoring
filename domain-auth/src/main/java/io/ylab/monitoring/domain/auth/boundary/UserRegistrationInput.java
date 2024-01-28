package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.exception.UserExistsException;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

/**
 * Сценарий "Регистрация"
 */
public interface UserRegistrationInput extends MonitoringInput {
    boolean register(UserRegistrationInputRequest request) throws UserExistsException;
}
