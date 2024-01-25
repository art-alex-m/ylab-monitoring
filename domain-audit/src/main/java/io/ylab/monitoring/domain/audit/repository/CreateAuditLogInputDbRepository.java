package io.ylab.monitoring.domain.audit.repository;

import io.ylab.monitoring.domain.audit.model.AuditItem;

public interface CreateAuditLogInputDbRepository {
    boolean create(AuditItem auditItem);
}
