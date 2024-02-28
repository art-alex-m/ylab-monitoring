package io.ylab.monitoring.app.springboot.starter.timelogaspect;

import io.ylab.monitoring.app.springboot.aspect.advice.TimeProfileLogAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Авто конфигурация подключения аспекта профилирования
 *
 * <p>
 * <a href="https://struchkov.dev/blog/ru/create-spring-boot-starter/">Создаем свой Spring Boot Starter</a><br>
 * <a href="https://www.baeldung.com/spring-boot-custom-starter">Creating a Custom Starter with Spring Boot</a><br>
 * </p>
 */
@Configuration
public class TimelogAspectAutoConfiguration {
    @Bean
    public TimeProfileLogAdvice timeProfileLogAdvice() {
        return new TimeProfileLogAdvice();
    }
}
