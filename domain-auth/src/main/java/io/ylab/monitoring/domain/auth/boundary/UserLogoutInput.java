package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

/**
 * Сценарий "Выход из системы"
 */
public interface UserLogoutInput extends MonitoringInput {
    /**
     * Выход пользователя из системы
     *
     * @param request объект запроса
     * @return истина
     */
    boolean logout(UserLogoutInputRequest request);
}
