package io.ylab.monitoring.app.servlets.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Кеширование конфигурации
 */
@Aspect
public class ConfigurationCacheAdvice {

    private final Map<Signature, Object> cacheRepository = new ConcurrentHashMap<>();

    @Pointcut("execution(public * io.ylab.monitoring.app.servlets.config.*Configuration.*())")
    public void isConfig() {
    }

    @Around("isConfig() && !Pointcuts.classConstructor()")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        if (cacheRepository.containsKey(joinPoint.getSignature())) {
            return cacheRepository.get(joinPoint.getSignature());
        }

        Object result = joinPoint.proceed();
        if (result != null) {
            cacheRepository.putIfAbsent(joinPoint.getSignature(), result);
        }

        return result;
    }
}
