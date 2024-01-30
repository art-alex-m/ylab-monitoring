package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.exception.UserNotFoundException;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

/**
 * Сценарий "Вход в систему"
 */
public interface UserLoginInput extends MonitoringInput {
    /**
     * Вход пользователя
     *
     * @param request объект запроса
     * @return истина
     * @throws UserNotFoundException когда пользователь не найден или неверные учетные данные
     */
    boolean login(UserLoginInputRequest request) throws UserNotFoundException;
}
