package io.ylab.monitoring.auth.out;

import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import io.ylab.monitoring.domain.core.model.DomainRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AuthUserLoginInputResponse implements UserLoginInputResponse {
    private final UUID id;

    private final DomainRole role;
}
