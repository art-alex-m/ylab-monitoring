package io.ylab.monitoring.audit.event;

import io.ylab.monitoring.domain.audit.event.ViewAuditLogFound;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class AuditViewAuditLogFound extends AuditMonitoringEvent implements ViewAuditLogFound {
    private final String eventName = "finish use case 'view audit log'";

    private final List<AuditItem> auditLog;
}
