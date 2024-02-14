package io.ylab.monitoring.app.springmvc.out;

import io.ylab.monitoring.app.springmvc.mapper.AuditItemAppAuditItemMapper;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppAuditLogInputResponseFactory implements ViewAuditLogInputResponseFactory {

    private final AuditItemAppAuditItemMapper mapper;

    @Override
    public ViewAuditLogInputResponse create(List<AuditItem> auditItemList) {
        return new AppAuditLogInputResponse(mapper.from(auditItemList));
    }
}
