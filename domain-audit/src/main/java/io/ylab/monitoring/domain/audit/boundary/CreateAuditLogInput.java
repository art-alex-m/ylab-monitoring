package io.ylab.monitoring.domain.audit.boundary;

import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;

public interface CreateAuditLogInput {
    boolean create(CreateAuditLogInputRequest request);
}
