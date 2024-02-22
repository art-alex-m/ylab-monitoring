package io.ylab.monitoring.app.springboot.aspect.advice;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Профилирование операций
 */
@Log
@Aspect
public class TimeProfileLogAdvice {

    /**
     * Тайм профилирование любого публичного метода класса, аннотированного @TimeProfileLog
     */
    @Around("Pointcuts.anyPublicMethod() && Pointcuts.timeProfileAnnotatedType()")
    public Object profileAnnotated(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime startTime = LocalDateTime.now();
        String currentClass = joinPoint.getTarget().getClass().getName();
        String currentMethod = joinPoint.getSignature().getName();
        log.info(String.format("Start invoke method %s::%s", currentClass, currentMethod));
        try {
            Object result = joinPoint.proceed();
            log.info(String.format("Finish method %s::%s with time %.3f ms", currentClass, currentMethod,
                    ChronoUnit.MICROS.between(startTime, LocalDateTime.now()) / 10000f));
            return result;
        } catch (Exception ex) {
            log.severe(String.format("Invoke of %s::%s throws %s", currentClass, currentMethod, ex.getMessage()));
            throw ex;
        }
    }

    /**
     * Тайм профилирование любого публичного метода класса, аннотированного @RestController
     */
    @Around("Pointcuts.anyPublicMethod() && Pointcuts.restAnnotatedControllers()")
    public Object profileRestController(ProceedingJoinPoint joinPoint) throws Throwable {
        return profileAnnotated(joinPoint);
    }
}
