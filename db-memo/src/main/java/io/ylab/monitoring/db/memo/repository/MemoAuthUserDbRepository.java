package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoAuthUserDbRepository implements UserLoginInputDbRepository, UserRegistrationInputDbRepository {

    private final Map<String, AuthUser> database = new HashMap<>();

    @Override
    public boolean create(AuthUser user) {
        return database.putIfAbsent(user.getUsername(), user) == null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return database.containsKey(username);
    }

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        return Optional.ofNullable(database.get(username));
    }
}
