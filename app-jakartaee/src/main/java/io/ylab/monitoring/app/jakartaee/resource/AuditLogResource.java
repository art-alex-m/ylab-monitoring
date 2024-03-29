package io.ylab.monitoring.app.jakartaee.resource;

import io.ylab.monitoring.app.jakartaee.interceptor.AuditLogger;
import io.ylab.monitoring.app.jakartaee.service.AppUserContext;
import io.ylab.monitoring.audit.in.AuditViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Просмотр лога аудита
 */
@AuditLogger
@Path("/admin/audit-logs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN"})
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResource {

    @Inject
    private ViewAuditLogInput auditLogInteractor;

    @Inject
    private AppUserContext userContext;

    @GET
    public List<? extends AuditItem> auditLog() {
        ViewAuditLogInputRequest request = new AuditViewAuditLogInputRequest(userContext.getCurrentUser());
        return auditLogInteractor.view(request).getAuditLog();
    }
}
