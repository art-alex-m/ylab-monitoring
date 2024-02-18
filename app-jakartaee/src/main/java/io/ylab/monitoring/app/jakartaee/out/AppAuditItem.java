package io.ylab.monitoring.app.jakartaee.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class AppAuditItem implements AuditItem {
    private final Instant occurredAt;

    private final DomainUser user;

    private final String name;
}
