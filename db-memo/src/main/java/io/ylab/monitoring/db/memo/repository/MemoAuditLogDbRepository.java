package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MemoAuditLogDbRepository implements CreateAuditLogInputDbRepository, ViewAuditLogInputDbRepository {

    private final Deque<AuditItem> database = new LinkedList<>();

    @Override
    public boolean create(AuditItem auditItem) {
        database.addFirst(auditItem);
        return true;
    }

    @Override
    public List<AuditItem> findAll() {
        return database.stream().toList();
    }
}
