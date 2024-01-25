package io.ylab.monitoring.domain.audit.event;

import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;

public interface ViewAuditLogEntered extends MonitoringEvent {
    ViewAuditLogInputRequest getRequest();
}
