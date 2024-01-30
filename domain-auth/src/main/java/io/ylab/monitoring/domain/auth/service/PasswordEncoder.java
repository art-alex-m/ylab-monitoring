package io.ylab.monitoring.domain.auth.service;

import io.ylab.monitoring.domain.auth.exception.PasswordEncoderException;

/**
 * Сервис создания и проверки хешей пароля
 */
public interface PasswordEncoder {
    /**
     * Создает хеш пароля
     *
     * @param rawPassword Строка
     * @return хеш
     */
    String encode(String rawPassword) throws PasswordEncoderException;

    /**
     * Проверка совпадает ли пароль с образцом хеша
     *
     * @param rawPassword     пароль
     * @param encodedPassword образец, хеш
     * @return истина, если хеш введенного пароля соответствует образцу хеша
     */
    boolean matches(String rawPassword, String encodedPassword);
}
