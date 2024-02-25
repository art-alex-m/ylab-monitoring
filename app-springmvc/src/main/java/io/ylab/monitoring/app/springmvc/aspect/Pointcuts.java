package io.ylab.monitoring.app.springmvc.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Срезы аспектов
 */
public class Pointcuts {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restAnnotatedControllers() {
    }

    @Pointcut("within(@io.ylab.monitoring.app.springmvc.aspect.TimeProfileLog *)")
    public void timeProfileAnnotatedType() {
    }

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
    }

    /**
     * Методы сценариев ядра
     */
    @Pointcut("within(io.ylab.monitoring.*.boundary.*Interactor)")
    public void boundaryInputMethod() {
    }

    /**
     * Сценарий создания лога аудита
     */
    @Pointcut("target(io.ylab.monitoring.audit.boundary.AuditCreateAuditLogInteractor)")
    public void auditLogCreateInteractor() {
    }
}
