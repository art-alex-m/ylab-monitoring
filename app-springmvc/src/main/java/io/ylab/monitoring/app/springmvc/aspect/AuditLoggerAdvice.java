package io.ylab.monitoring.app.springmvc.aspect;


import io.ylab.monitoring.app.springmvc.service.AppUserContext;
import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Аспект ведения лога аудита
 */
@Aspect
@Component
@AllArgsConstructor
public class AuditLoggerAdvice {

    private final ApplicationEventPublisher eventPublisher;

    private final AppUserContext userContext;

    @Before("!Pointcuts.auditLogCreateInteractor() && Pointcuts.boundaryInputMethod() && Pointcuts.anyPublicMethod()")
    public void createAuditLog(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String message = String.format("Invoke %s::%s", className, method);

        eventPublisher.publishEvent(AuditCreateAuditLogInputRequest.builder()
                .name(message)
                .user(userContext.getCurrentUser())
                .occurredAt(Instant.now()).build());
    }
}
