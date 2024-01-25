package io.ylab.monitoring.domain.audit.model;

import io.ylab.monitoring.domain.core.model.DomainUser;

import java.time.Instant;

public interface AuditItem {
    Instant getOccurredAt();

    DomainUser getUser();

    String getName();
}
