package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class AppViewAuditLogInputResponse implements ViewAuditLogInputResponse {
    private final List<AuditItem> auditLog;

    private final static String SEPARATOR = " | ";
    private final static String HEAD = "OCCURRED_AT | USER_ID | EVENT_NAME";

    @Override
    public String toString() {
        if (auditLog.isEmpty()) {
            return "No audit log records";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HEAD).append("\n");
        auditLog.forEach(item -> stringBuilder
                .append(item.getOccurredAt())
                .append(SEPARATOR)
                .append(item.getUser().getId())
                .append(SEPARATOR)
                .append(item.getName())
                .append("\n"));

        return stringBuilder.toString();
    }
}
