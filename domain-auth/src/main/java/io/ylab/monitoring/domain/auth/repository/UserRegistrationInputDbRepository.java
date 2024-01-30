package io.ylab.monitoring.domain.auth.repository;

import io.ylab.monitoring.domain.auth.model.AuthUser;

/**
 * Репозиторий пользовательских учетных записей в сценарии "Регистрация"
 */
public interface UserRegistrationInputDbRepository {
    /**
     * Сохраняет информацию о пользователе
     *
     * @param user Учетные данные
     * @return Истина когда пользователь добавлен в базу
     */
    boolean create(AuthUser user);

    /**
     * Проверка учетной информации по логину пользователя
     *
     * @param username Логин
     * @return Истина если логин уже занят
     */
    boolean existsByUsername(String username);
}
