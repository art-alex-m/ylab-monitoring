package io.ylab.monitoring.app.console.model;

import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AppUserContext {
    private DomainUser user;

    private DomainRole role;

    public void setUser(AuthUser user) {
        if (user == null) {
            setAnonymous();
            return;
        }
        this.role = user.getRole();
        this.user = new User(user.getId());
    }

    public void setAnonymous() {
        this.user = null;
        this.role = DomainRole.ANONYMOUS;
    }

    @Getter
    @AllArgsConstructor
    private static class User implements DomainUser {
        private final UUID id;
    }
}
