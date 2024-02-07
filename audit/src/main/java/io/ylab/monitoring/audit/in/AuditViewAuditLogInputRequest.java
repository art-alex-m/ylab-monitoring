package io.ylab.monitoring.audit.in;

import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@inheritDoc}
 */
@AllArgsConstructor
@Getter
public class AuditViewAuditLogInputRequest implements ViewAuditLogInputRequest {
    private final DomainUser user;
}
