package io.ylab.monitoring.app.servlets.event;

import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

/**
 * Запись лога аудита через механизм событий CDI
 */
@ApplicationScoped
public class AppCreateAuditLogEventHandler implements MonitoringEventHandler {

    @Inject
    private CreateAuditLogInput interactor;

    @Override
    public boolean handle(@ObservesAsync MonitoringEvent event) {

        interactor.create(AuditCreateAuditLogInputRequest.builder()
                .name(event.getEventName())
                .user(event.getUser())
                .occurredAt(event.getCreatedAt()).build());

        return true;
    }
}
