package io.ylab.monitoring.domain.audit.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;

import java.util.List;

public interface ViewAuditLogInputResponse {
    List<AuditItem> getAuditLog();
}
