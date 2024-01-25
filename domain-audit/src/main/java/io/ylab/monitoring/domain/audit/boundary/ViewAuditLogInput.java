package io.ylab.monitoring.domain.audit.boundary;

import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;

public interface ViewAuditLogInput {
    ViewAuditLogInputResponse view(ViewAuditLogInputRequest request);
}
