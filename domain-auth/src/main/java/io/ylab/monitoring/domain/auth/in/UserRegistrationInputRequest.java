package io.ylab.monitoring.domain.auth.in;

/**
 * Запрос регистрации пользователя
 */
public interface UserRegistrationInputRequest {
    /**
     * Логин пользователя
     *
     * @return строка
     */
    String getUsername();

    /**
     * Пароль пользователя
     * @return строка
     */
    String getPassword();
}
