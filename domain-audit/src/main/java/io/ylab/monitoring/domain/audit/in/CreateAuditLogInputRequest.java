package io.ylab.monitoring.domain.audit.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

import java.time.Instant;

public interface CreateAuditLogInputRequest {
    Instant getOccurredAt();

    DomainUser getUser();

    String getName();
}
