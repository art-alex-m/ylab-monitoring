package io.ylab.monitoring.app.jakartaee.interceptor;

import io.ylab.monitoring.app.jakartaee.service.AppUserContext;
import io.ylab.monitoring.audit.in.AuditCreateAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.in.CreateAuditLogInputRequest;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.time.Instant;

/**
 * Аспект ведения лога аудита
 *
 * <p>
 * <a href="https://javarush.com/groups/posts/2112-kratkiy-ehkskurs-v-vnedrenie-zavisimostey-ili-chto-ejshje-za-cdi">Краткий экскурс в внедрение зависимостей или "Что ещё за CDI?"</a><br>
 * </p>
 */
@Interceptor
@AuditLogger
public class AuditLoggerInterceptor {

    @Inject
    private Event<CreateAuditLogInputRequest> auditLogEvent;

    @Inject
    private AppUserContext userContext;

    @AroundInvoke
    public Object createAuditLog(InvocationContext ctx) throws Exception {
        String className = ctx.getMethod().getDeclaringClass().getName();
        String method = ctx.getMethod().getName();
        String message = String.format("Invoke %s::%s", className, method);

        auditLogEvent.fireAsync(AuditCreateAuditLogInputRequest.builder()
                .name(message)
                .user(userContext.getCurrentUser())
                .occurredAt(Instant.now()).build());

        return ctx.proceed();
    }

}
