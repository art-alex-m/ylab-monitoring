package io.ylab.monitoring.domain.audit.repository;

import io.ylab.monitoring.domain.audit.model.AuditItem;

import java.util.List;

/**
 * Репозиторий логов аудита в сценарии "Просмотр лога действий"
 */
public interface ViewAuditLogInputDbRepository {

    /**
     * Все записи лога аудита
     *
     * @return список
     */
    List<AuditItem> findAll();
}
