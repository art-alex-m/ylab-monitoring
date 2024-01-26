package io.ylab.monitoring.audit.model;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Builder
public class AuditAuditItem implements AuditItem {
    @NonNull
    private final Instant occurredAt;

    @NonNull
    private final DomainUser user;

    @NonNull
    private final String name;
}
