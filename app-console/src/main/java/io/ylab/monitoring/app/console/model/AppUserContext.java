package io.ylab.monitoring.app.console.model;

import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Контекст авторизованного пользователя
 */
@Getter
public class AppUserContext {
    private DomainUser user;

    private DomainRole role;

    /**
     * Установить контекст зарегистрированного пользователя
     *
     * @param user AuthUser
     * @return истина
     */
    public boolean setUser(AuthUser user) {
        if (user == null) {
            return setAnonymous();
        }
        this.role = user.getRole();
        this.user = new User(user.getId());
        return true;
    }

    /**
     * Устанавливает контекст анонимного пользователя
     *
     * @return истина
     */
    public boolean setAnonymous() {
        this.user = null;
        this.role = DomainRole.ANONYMOUS;
        return true;
    }

    /**
     * Информация о доменном пользователе для хранения в контексте
     */
    @Getter
    @AllArgsConstructor
    private static class User implements DomainUser {
        private final UUID id;
    }
}
