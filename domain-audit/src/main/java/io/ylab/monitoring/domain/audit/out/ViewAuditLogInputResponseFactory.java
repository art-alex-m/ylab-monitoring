package io.ylab.monitoring.domain.audit.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;

import java.util.List;

/**
 * Фабрика создания ответов для сценария "Просмотр лога действий"
 */
public interface ViewAuditLogInputResponseFactory {
    /**
     * Создает ответ для сценария "Просмотр лога действий"
     *
     * @param auditItemList список
     * @return объект
     */
    ViewAuditLogInputResponse create(List<AuditItem> auditItemList);
}
