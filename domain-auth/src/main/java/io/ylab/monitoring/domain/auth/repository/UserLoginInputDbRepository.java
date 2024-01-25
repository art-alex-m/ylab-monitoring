package io.ylab.monitoring.domain.auth.repository;

import io.ylab.monitoring.domain.auth.model.AuthUser;

import java.util.Optional;

public interface UserLoginInputDbRepository {
    Optional<AuthUser> findByUsername(String username);
}
