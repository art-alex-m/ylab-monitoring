package io.ylab.monitoring.auth.model;

import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class AuthAuthUser implements AuthUser {
    @NonNull
    private final String username;
    @NonNull
    private final String password;
    @NonNull
    private final DomainRole role;
    @Builder.Default
    private UUID id = UUID.randomUUID();
}
