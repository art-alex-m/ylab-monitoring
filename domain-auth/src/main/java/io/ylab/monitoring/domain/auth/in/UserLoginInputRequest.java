package io.ylab.monitoring.domain.auth.in;

/**
 * Запрос идентификации пользователя в системе
 */
public interface UserLoginInputRequest {
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
