package io.ylab.monitoring.app.servlets.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Срезы аспектов
 */
public class Pointcuts {
    @Pointcut("preinitialization(*.new(..)) || initialization(*.new(..))")
    public void classConstructor() {
    }

    @Pointcut("execution(* io.ylab.monitoring.app.servlets.servlet.*Servlet.*(..))")
    public void anyServlet() {
    }

    @Pointcut("@within(io.ylab.monitoring.app.servlets.aspect.TimeProfileLog)")
    public void timeProfileAnnotatedType() {
    }

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
    }

    /**
     * Методы сценариев ядра
     */
    @Pointcut("execution(public * io.ylab.monitoring.app.servlets.servlet.*Servlet.do*(..))")
    public void boundaryInputMethod() {
    }

    /**
     * Сценарий создания лога аудита
     */
    @Pointcut("target(io.ylab.monitoring.audit.boundary.AuditCreateAuditLogInteractor)")
    public void auditLogCreateInteractor() {
    }
}
