package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class JdbcAuditLogDbRepository implements CreateAuditLogInputDbRepository, ViewAuditLogInputDbRepository {

    private Connection connection;

    @Override
    public boolean create(AuditItem auditItem) {
        return false;
    }

    @Override
    public List<AuditItem> findAll() {
        return Collections.emptyList();
    }
}
