package io.ylab.monitoring.audit.model;

import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuditDomainUser implements DomainUser {
    public static final DomainUser NULL_USER = new AuditDomainUser(
            UUID.fromString("01000000-0000-0000-0000-000000000010"));

    private final UUID id;
}
