package io.ylab.monitoring.audit.boundary;

import io.ylab.monitoring.audit.model.AuditAuditItem;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
public class AuditCreateAuditLogInteractor implements CreateAuditLogInput {

    private final CreateAuditLogInputDbRepository inputDbRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(CreateAuditLogInputRequest request) {

        inputDbRepository.create(AuditAuditItem.builder()
                .user(request.getUser())
                .occurredAt(request.getOccurredAt())
                .name(request.getName()).build());

        return true;
    }
}
