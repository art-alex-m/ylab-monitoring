package io.ylab.monitoring.domain.audit.event;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;

import java.util.List;

public interface ViewAuditLogFound extends MonitoringEvent {
    List<AuditItem> getAuditLog();
}
