package io.ylab.monitoring.audit.event;

import io.ylab.monitoring.domain.audit.event.ViewAuditLogEntered;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class AuditViewAuditLogEntered extends AuditMonitoringEvent implements ViewAuditLogEntered {
    private static final String EVENT_NAME = "Enter in use case 'view audit log'";

    private final ViewAuditLogInputRequest request;

    @Builder
    public AuditViewAuditLogEntered(DomainUser user, Instant createdAt, ViewAuditLogInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }


    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
