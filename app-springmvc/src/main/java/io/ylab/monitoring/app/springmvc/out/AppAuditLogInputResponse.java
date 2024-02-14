package io.ylab.monitoring.app.springmvc.out;

import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AppAuditLogInputResponse implements ViewAuditLogInputResponse {
    private final List<AppAuditItem> auditLog;
}
