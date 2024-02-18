package io.ylab.monitoring.app.servlets.event;

import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;
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
        return handle(AuditCreateAuditLogInputRequest.builder()
                .name(event.getEventName())
                .user(event.getUser())
                .occurredAt(event.getCreatedAt()).build());
    }

    /**
     * Ведение лога аудита через аспекты @AuditLogger
     *
     * @param request AuditCreateAuditLogInputRequest
     * @return boolean
     */
    public boolean handle(@ObservesAsync CreateAuditLogInputRequest request) {
        return interactor.create(request);
    }
}
