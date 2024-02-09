package io.ylab.monitoring.app.servlets.event;

import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class AppCreateAuditLogEventHandler implements MonitoringEventHandler {

    @Inject
    private CreateAuditLogInput interactor;

    @Override
    public boolean handle(@Observes MonitoringEvent event) {

        interactor.create(AuditCreateAuditLogInputRequest.builder()
                .name(event.getEventName())
                .user(event.getUser())
                .occurredAt(event.getCreatedAt()).build());

        return true;
    }
}
