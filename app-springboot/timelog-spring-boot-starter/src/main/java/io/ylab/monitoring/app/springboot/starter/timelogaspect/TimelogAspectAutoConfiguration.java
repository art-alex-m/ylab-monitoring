package io.ylab.monitoring.app.springboot.starter.timelogaspect;

import io.ylab.monitoring.app.springboot.aspect.advice.TimeProfileLogAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimelogAspectAutoConfiguration {
    @Bean
    public TimeProfileLogAdvice timeProfileLogAdvice() {
        return new TimeProfileLogAdvice();
    }
}
