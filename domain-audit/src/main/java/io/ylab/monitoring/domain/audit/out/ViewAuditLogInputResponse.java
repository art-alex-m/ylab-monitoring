package io.ylab.monitoring.domain.audit.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;

import java.util.List;

/**
 * Ответ в сценарии "Просмотр лога действий"
 */
public interface ViewAuditLogInputResponse {
    /**
     * Список записей лога аудита
     *
     * @return список записей или пустой список
     */
    List<AuditItem> getAuditLog();
}
