package io.ylab.monitoring.app.servlets.aspect;


import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.time.Instant;

/**
 * Аспект ведения лога аудита
 */
@Aspect
@Log
@AllArgsConstructor
public class AuditLoggerAdvice {

    private final CreateAuditLogInput auditLogInput;

    private final AppUserContext userContext;

    public AuditLoggerAdvice() {
        this.auditLogInput = AppConfiguration.REGISTRY.createAuditLogInteractor();
        this.userContext = AppConfiguration.REGISTRY.appUserContext();
    }

    @Before("!Pointcuts.auditLogCreateInteractor() && Pointcuts.boundaryInputMethod() && args(req, ..)")
    public void createAuditLog(JoinPoint joinPoint, HttpServletRequest req) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().getName();
        String message = String.format("Invoke %s::%s", className, method);

        auditLogInput.create(AuditCreateAuditLogInputRequest.builder()
                .user(userContext.getCurrentUser(req))
                .name(message)
                .occurredAt(Instant.now()).build());
    }
}
