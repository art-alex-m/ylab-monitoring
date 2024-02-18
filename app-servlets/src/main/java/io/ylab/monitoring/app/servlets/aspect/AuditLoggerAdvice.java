package io.ylab.monitoring.app.servlets.aspect;


import io.ylab.monitoring.app.servlets.service.AppUserContext;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Аспект ведения лога аудита
 */
@Aspect
@AllArgsConstructor
public class AuditLoggerAdvice {

//    private final ApplicationEventPublisher eventPublisher;

    private final AppUserContext userContext;

    @Before("!Pointcuts.auditLogCreateInteractor() && Pointcuts.boundaryInputMethod() && Pointcuts.anyPublicMethod()")
    public void createAuditLog(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String message = String.format("Invoke %s::%s", className, method);

//        eventPublisher.publishEvent(AuditCreateAuditLogInputRequest.builder()
//                .name(message)
//                .user(userContext.getCurrentUser())
//                .occurredAt(Instant.now()).build());
    }
}
