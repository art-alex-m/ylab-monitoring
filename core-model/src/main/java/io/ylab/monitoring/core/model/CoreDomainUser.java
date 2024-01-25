package io.ylab.monitoring.core.model;

import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CoreDomainUser implements DomainUser {
    private final UUID id;
}
