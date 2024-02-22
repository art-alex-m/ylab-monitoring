package io.ylab.monitoring.app.springboot.starter.auditlogaspect;

import io.ylab.monitoring.app.springboot.aspect.advice.AuditLoggerAdvice;
import io.ylab.monitoring.domain.core.model.UserContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

/**
 * Конфигурация аспекта создания лога адита
 */
public class AuditlogAspectAutoConfiguration {
    @Bean
    @ConditionalOnBean(UserContext.class)
    public AuditLoggerAdvice auditLoggerAdvice(ApplicationEventPublisher eventPublisher, UserContext userContext) {
        return new AuditLoggerAdvice(eventPublisher, userContext);
    }
}
