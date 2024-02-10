package io.ylab.monitoring.app.servlets.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@Interceptor
@TimeProfileLog
public class TimeProfileLogInterceptor {

    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    @AroundInvoke
    public Object profile(InvocationContext ctx) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        String currentClass = ctx.getMethod().getDeclaringClass().getName();
        String currentMethod = ctx.getMethod().getName();
        logger.info(String.format("Start invoke method %s::%s", currentClass, currentMethod));
        try {
            Object result = ctx.proceed();
            logger.info(String.format("Finish method %s::%s with time %d ms", currentClass, currentMethod,
                    ChronoUnit.MICROS.between(startTime, LocalDateTime.now())));
            return result;
        } catch (Exception ex) {
            logger.severe(String.format("Invoke of %s::%s throws %s", currentClass, currentMethod, ex.getMessage()));
            throw ex;
        }
    }

}
