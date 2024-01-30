package io.ylab.monitoring.domain.auth.repository;

import io.ylab.monitoring.domain.auth.model.AuthUser;

import java.util.Optional;

/**
 * Репозиторий пользовательских учетных записей в сценарии "Вход в систему"
 */
public interface UserLoginInputDbRepository {
    /**
     * Поиск учетной информации по логину пользователя
     *
     * @param username Логин
     * @return Объект
     */
    Optional<AuthUser> findByUsername(String username);
}
