package io.ylab.monitoring.app.springmvc.controller;

import io.ylab.monitoring.app.springmvc.service.AppUserContext;
import io.ylab.monitoring.audit.in.AuditViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Просмотр лога аудита
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed("ADMIN")
public class AuditLogController {

    private final ViewAuditLogInput auditLogInteractor;

    private final AppUserContext userContext;

    @GetMapping("/audit-logs")
    public List<? extends AuditItem> auditLog() {
        ViewAuditLogInputRequest request = new AuditViewAuditLogInputRequest(userContext.getCurrentUser());
        return auditLogInteractor.view(request).getAuditLog();
    }
}
