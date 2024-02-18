package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.app.servlets.mapper.AuditItemAppAuditItemMapper;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AppAuditLogInputResponseFactory implements ViewAuditLogInputResponseFactory {

    @Inject
    private AuditItemAppAuditItemMapper mapper;

    @Override
    public ViewAuditLogInputResponse create(List<AuditItem> auditItemList) {
        return new AppAuditLogInputResponse(mapper.from(auditItemList));
    }
}
