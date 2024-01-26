package io.ylab.monitoring.domain.auth.boundary;

import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

/**
 * Сценарий "Выход из системы"
 */
public interface UserLogoutInput extends MonitoringInput {
    boolean logout(UserLogoutInputRequest request);
}
