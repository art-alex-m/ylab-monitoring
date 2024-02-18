package io.ylab.monitoring.app.servlets.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Срезы аспектов
 */
public class Pointcuts {
    @Pointcut("preinitialization(*.new()) || preinitialization(*.new(..)) || initialization(*.new()) || initialization(*.new(..))")
    public void classConstructor() {
    }

    @Pointcut("within(io.ylab.monitoring.app.servlets.servlet.*Servlet)")
    public void anyServlet() {
    }

    @Pointcut("within(@io.ylab.monitoring.app.servlets.aspect.TimeProfileLog *)")
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
