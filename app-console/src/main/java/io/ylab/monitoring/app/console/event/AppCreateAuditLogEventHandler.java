package io.ylab.monitoring.app.console.event;

import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventHandler;
import lombok.RequiredArgsConstructor;

/**
 * Обработчик событий для ведения лога аудита
 */
@RequiredArgsConstructor
public class AppCreateAuditLogEventHandler implements MonitoringEventHandler {

    private final CreateAuditLogInput interactor;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(MonitoringEvent event) {

        interactor.create(AuditCreateAuditLogInputRequest.builder()
                .name(event.getEventName())
                .user(event.getUser())
                .occurredAt(event.getCreatedAt()).build());

        return true;
    }
}
