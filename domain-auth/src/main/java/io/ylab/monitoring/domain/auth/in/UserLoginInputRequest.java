package io.ylab.monitoring.domain.auth.in;

/**
 * Запрос идентификации пользователя в системе
 */
public interface UserLoginInputRequest {
    String getUsername();

    String getPassword();
}
