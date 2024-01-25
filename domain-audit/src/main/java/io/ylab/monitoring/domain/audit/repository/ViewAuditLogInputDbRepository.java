package io.ylab.monitoring.domain.audit.repository;

import io.ylab.monitoring.domain.audit.model.AuditItem;

import java.util.List;

public interface ViewAuditLogInputDbRepository {
    List<AuditItem> findAll();
}
