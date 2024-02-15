package io.ylab.monitoring.app.springmvc.event;

import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Запись лога аудита через механизм событий
 */
@Component
@AllArgsConstructor
public class AppCreateAuditLogEventHandler implements MonitoringEventHandler {

    private final CreateAuditLogInput interactor;

    @Override
    @EventListener(MonitoringEvent.class)
    public boolean handle(MonitoringEvent event) {
        return handle(AuditCreateAuditLogInputRequest.builder()
                .name(event.getEventName())
                .user(event.getUser())
                .occurredAt(event.getCreatedAt()).build());
    }

    /**
     * Ведение лога аудита через аспекты
     *
     * @param request AuditCreateAuditLogInputRequest
     * @return boolean
     */
    @EventListener(CreateAuditLogInputRequest.class)
    public boolean handle(CreateAuditLogInputRequest request) {
        return interactor.create(request);
    }
}
