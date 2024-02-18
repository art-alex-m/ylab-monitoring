package io.ylab.monitoring.app.servlets.out;


import io.ylab.monitoring.app.servlets.mapper.AuditItemAppAuditItemMapper;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AppAuditLogInputResponseFactory implements ViewAuditLogInputResponseFactory {

    private final AuditItemAppAuditItemMapper mapper;

    @Override
    public ViewAuditLogInputResponse create(List<AuditItem> auditItemList) {
        return new AppAuditLogInputResponse(mapper.from(auditItemList));
    }
}
