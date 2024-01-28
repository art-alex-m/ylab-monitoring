package io.ylab.monitoring.domain.audit.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;

import java.util.List;

/**
 * Фабрика создания ответов для сценария "Просмотр лога действий"
 */
public interface ViewAuditLogInputResponseFactory {
    ViewAuditLogInputResponse create(List<AuditItem> auditItemList);
}
