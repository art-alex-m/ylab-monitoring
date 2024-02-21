package io.ylab.monitoring.app.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springboot.config.OpenapiTag;
import io.ylab.monitoring.app.springboot.out.AppAuditItem;
import io.ylab.monitoring.app.springboot.service.AppUserContext;
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
@Tag(name = OpenapiTag.AUDIT)
@Tag(name = OpenapiTag.ADMIN)
@AllArgsConstructor
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed("ADMIN")
public class AuditLogController {

    private final ViewAuditLogInput auditLogInteractor;

    private final AppUserContext userContext;


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
