package io.ylab.monitoring.app.springmvc.model;

import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
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

    private final UUID id;

    private final Collection<? extends GrantedAuthority> authorities;

    private final String username;

    private final String password = "[SECURED]";

    public AppUserDetails(UUID id, Collection<? extends GrantedAuthority> authorities, String username) {
        this.id = id;
        this.authorities = authorities;
        this.username = username;
    }

    public AppUserDetails(UserLoginInputResponse response) {
        this.id = response.getId();
        this.authorities = Set.of(new AppGrantedAuthority(response.getRole().name()));
        this.username = response.getId().toString();
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

    @AllArgsConstructor
    @Getter
    public static class AppGrantedAuthority implements GrantedAuthority {
        private final String authority;
    }
}
