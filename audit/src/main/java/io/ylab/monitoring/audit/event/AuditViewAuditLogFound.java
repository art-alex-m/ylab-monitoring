package io.ylab.monitoring.audit.event;

import io.ylab.monitoring.domain.audit.event.ViewAuditLogFound;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class AuditViewAuditLogFound extends AuditMonitoringEvent implements ViewAuditLogFound {
    private static final String EVENT_NAME = "Finish use case 'view audit log'";

    private final List<AuditItem> auditLog;

    @Builder
    public AuditViewAuditLogFound(DomainUser user, Instant createdAt, List<AuditItem> auditLog) {
        super(user, createdAt);
        this.auditLog = auditLog;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
