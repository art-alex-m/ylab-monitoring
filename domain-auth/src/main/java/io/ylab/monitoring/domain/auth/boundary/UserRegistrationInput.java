package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.exception.UserExistsException;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

/**
 * Сценарий "Регистрация"
 */
public interface UserRegistrationInput extends MonitoringInput {
    /**
     * Регистрация пользователя в системе
     *
     * @param request объект запроса
     * @return истина
     * @throws UserExistsException когда указанное имя пользователя уже занято
     */
    boolean register(UserRegistrationInputRequest request) throws UserExistsException;
}
