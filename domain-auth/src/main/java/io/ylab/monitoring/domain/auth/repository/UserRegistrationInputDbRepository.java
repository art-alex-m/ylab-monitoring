package io.ylab.monitoring.domain.auth.repository;

import io.ylab.monitoring.domain.auth.model.AuthUser;

import java.util.Optional;

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
     * Поиск учетной информации по логину пользователя
     *
     * @param username Логин
     * @return Объект
     */
    Optional<AuthUser> findByUsername(String username);
}
