package io.ylab.monitoring.audit.boundary;

import io.ylab.monitoring.audit.event.AuditViewAuditLogEntered;
import io.ylab.monitoring.audit.event.AuditViewAuditLogFound;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Builder
public class AuditViewAuditLogInteractor implements ViewAuditLogInput {

    private final ViewAuditLogInputDbRepository inputDbRepository;

    private final ViewAuditLogInputResponseFactory responseFactory;

    private final MonitoringEventPublisher eventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewAuditLogInputResponse view(ViewAuditLogInputRequest request) {

        eventPublisher.publish(AuditViewAuditLogEntered.builder()
                .user(request.getUser())
                .request(request).build());

        List<AuditItem> auditLog = inputDbRepository.findAll();

        eventPublisher.publish(AuditViewAuditLogFound.builder()
                .auditLog(auditLog)
                .user(request.getUser()).build());

        return responseFactory.create(auditLog);
    }
}
