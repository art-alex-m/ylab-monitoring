package io.ylab.monitoring.domain.audit.boundary;

import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

/**
 * Сценарий "Запись лога действий пользователей"
 */
public interface CreateAuditLogInput extends MonitoringInput {
    boolean create(CreateAuditLogInputRequest request);
}
