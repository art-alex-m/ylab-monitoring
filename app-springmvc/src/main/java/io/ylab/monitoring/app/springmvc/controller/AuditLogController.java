package io.ylab.monitoring.app.springmvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springmvc.config.OpenapiTag;
import io.ylab.monitoring.app.springmvc.out.AppAuditItem;
import io.ylab.monitoring.app.springmvc.service.AppUserContext;
import io.ylab.monitoring.audit.in.AuditViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.in.ViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Просмотр лога аудита
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Tag(name = OpenapiTag.AUDIT)
@Tag(name = OpenapiTag.ADMIN)
@AllArgsConstructor
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed("ADMIN")
public class AuditLogController {

    private final ViewAuditLogInput auditLogInteractor;

    private final AppUserContext userContext;

    @Path(("/audit-logs"))
    @GET
    @Operation(summary = "View audit logs", responses = {
            @ApiResponse(responseCode = "200", description = "Audit log",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppAuditItem.class))))
    })
    @GetMapping("/audit-logs")
    public List<? extends AuditItem> auditLog() {
        ViewAuditLogInputRequest request = new AuditViewAuditLogInputRequest(userContext.getCurrentUser());
        return auditLogInteractor.view(request).getAuditLog();
    }
}
