package io.ylab.monitoring.domain.audit.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

public interface ViewAuditLogInputRequest {
    DomainUser getUser();
}
