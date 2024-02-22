package io.ylab.monitoring.app.springboot.config;

import io.ylab.monitoring.app.springboot.starter.auditlogaspect.EnableAuditlogAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableAspectJAutoProxy
@EnableAuditlogAspect
@Configuration
public class AppBaseConfiguration implements WebMvcConfigurer {

    /**
     * Добавляет префикс /api к роутам rest контроллеров
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
