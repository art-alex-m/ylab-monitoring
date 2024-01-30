package io.ylab.monitoring.domain.audit.boundary;

import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

/**
 * Сценарий "Просмотр лога действий"
 */
public interface ViewAuditLogInput extends MonitoringInput {
    /**
     * Возвращает список записей лога аудита
     *
     * @param request запрос
     * @return объект ответа
     */
    ViewAuditLogInputResponse view(ViewAuditLogInputRequest request);
}
