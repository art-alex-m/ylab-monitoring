package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class AppViewAuditLogInputResponseFactory implements ViewAuditLogInputResponseFactory {
    @Override
    public ViewAuditLogInputResponse create(List<AuditItem> auditItemList) {
        return new AppViewAuditLogInputResponse(auditItemList);
    }
}
