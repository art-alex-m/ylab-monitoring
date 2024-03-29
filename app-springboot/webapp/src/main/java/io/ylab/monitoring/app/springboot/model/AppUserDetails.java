package io.ylab.monitoring.app.springboot.model;

import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * Представление авторизационных данных пользователя
 */
@Getter
public class AppUserDetails implements UserDetails, DomainUser {

    private static final String PASSWORD_STUB = "[SECURED]";

    private static final String ROLE_PREFIX = "ROLE_";

    private final UUID id;

    private final Collection<? extends GrantedAuthority> authorities;

    private final String username;

    public AppUserDetails(UUID id, Collection<? extends GrantedAuthority> authorities, String username) {
        this.id = id;
        this.authorities = authorities;
        this.username = username;
    }

    public AppUserDetails(UserLoginInputResponse response) {
        this.id = response.getId();
        this.authorities = Set.of(new AppGrantedAuthority(prepareRole(response.getRole())));
        this.username = response.getId().toString();
    }

    @Override
    public String getPassword() {
        return PASSWORD_STUB;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private String prepareRole(DomainRole domainRole) {
        return ROLE_PREFIX + domainRole.name();
    }

    @AllArgsConstructor
    @Getter
    public static class AppGrantedAuthority implements GrantedAuthority {
        private final String authority;
    }
}
