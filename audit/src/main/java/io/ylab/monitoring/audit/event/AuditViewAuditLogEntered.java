package io.ylab.monitoring.audit.event;

import io.ylab.monitoring.domain.audit.event.ViewAuditLogEntered;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuditViewAuditLogEntered extends AuditMonitoringEvent implements ViewAuditLogEntered {
    private final String eventName = "enter in use case 'view audit log'";

    private final ViewAuditLogInputRequest request;
}
