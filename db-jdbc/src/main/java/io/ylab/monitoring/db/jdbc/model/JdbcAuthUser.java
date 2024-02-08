package io.ylab.monitoring.db.jdbc.model;

import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Getter
public class JdbcAuthUser implements AuthUser {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ID = "uuid";
    public static final String ROLE = "role";

    @NonNull
    private final String username;
    @NonNull
    private final String password;
    @NonNull
    private final DomainRole role;
    @NonNull
    private final UUID id;
}
