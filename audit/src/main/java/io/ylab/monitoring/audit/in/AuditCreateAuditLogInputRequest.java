package io.ylab.monitoring.audit.in;

import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@Builder
public class AuditCreateAuditLogInputRequest implements CreateAuditLogInputRequest {
    private final DomainUser user;
    private final Instant occurredAt;
    private final String name;
}
