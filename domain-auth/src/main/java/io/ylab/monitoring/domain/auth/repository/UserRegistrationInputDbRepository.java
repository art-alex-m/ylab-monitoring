package io.ylab.monitoring.domain.auth.repository;

import io.ylab.monitoring.domain.auth.model.AuthUser;

import java.util.Optional;

public interface UserRegistrationInputDbRepository {
    boolean create(AuthUser user);

    Optional<AuthUser> findByUsername(String username);
}
