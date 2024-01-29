package io.ylab.monitoring.audit.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class AuditMonitoringEvent implements MonitoringEvent {
    private final DomainUser user;

    private final Instant createdAt;

    protected AuditMonitoringEvent(DomainUser user, Instant createdAt) {
        this.user = user;
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        this.createdAt = createdAt;
    }
}
