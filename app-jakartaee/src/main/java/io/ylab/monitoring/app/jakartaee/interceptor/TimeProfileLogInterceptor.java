package io.ylab.monitoring.app.jakartaee.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Interceptor
@TimeProfileLog
@Log
public class TimeProfileLogInterceptor {

    @AroundInvoke
    public Object profile(InvocationContext ctx) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        String currentClass = ctx.getMethod().getDeclaringClass().getName();
        String currentMethod = ctx.getMethod().getName();
        log.info(String.format("Start invoke method %s::%s", currentClass, currentMethod));
        try {
            Object result = ctx.proceed();
            log.info(String.format("Finish method %s::%s with time %.3f ms", currentClass, currentMethod,
                    ChronoUnit.MICROS.between(startTime, LocalDateTime.now()) / 10000f));
            return result;
        } catch (Exception ex) {
            log.severe(String.format("Invoke of %s::%s throws %s", currentClass, currentMethod, ex.getMessage()));
            throw ex;
        }
    }

}
