package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AppAuditLogInputResponseFactory implements ViewAuditLogInputResponseFactory {
    @Override
    public ViewAuditLogInputResponse create(List<AuditItem> auditItemList) {
        return new AppAuditLogInputResponse(auditItemList.stream()
                .map(item -> (AuditItem) new AppAuditItem(item.getOccurredAt(), item.getUser(), item.getName()))
                .toList());
    }
}
