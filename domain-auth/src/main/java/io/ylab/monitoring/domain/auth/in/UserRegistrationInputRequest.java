package io.ylab.monitoring.domain.auth.in;

/**
 * Запрос регистрации пользователя
 */
public interface UserRegistrationInputRequest {
    String getUsername();

    String getPassword();
}
