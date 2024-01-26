package io.ylab.monitoring.domain.audit.repository;

import io.ylab.monitoring.domain.audit.model.AuditItem;

/**
 * Репозиторий логов аудита в сценарии "Запись лога действий пользователей"
 */
public interface CreateAuditLogInputDbRepository {
    /**
     * Создать запись аудита
     *
     * @param auditItem запись аудита
     * @return Истина, если запись создана
     */
    boolean create(AuditItem auditItem);
}
