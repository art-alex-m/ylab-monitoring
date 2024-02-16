package io.ylab.monitoring.app.springmvc.config;

import io.ylab.monitoring.auth.boundary.AuthUserLoginInteractor;
import io.ylab.monitoring.auth.boundary.AuthUserRegistrationInteractor;
import io.ylab.monitoring.auth.service.AuthPasswordEncoder;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация сценариев регистрации, входа-выхода
 */
@Configuration
public class AppAuthConfiguration {

    @Bean
    public UserLoginInput userLoginInteractor(PasswordEncoder passwordEncoder,
            MonitoringEventPublisher monitoringEventPublisher,
            UserLoginInputDbRepository inputDbRepository) {

        return AuthUserLoginInteractor.builder()
                .passwordEncoder(passwordEncoder)
                .eventPublisher(monitoringEventPublisher)
                .inputDbRepository(inputDbRepository)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${ylab.monitoring.auth.password.salt}") String passwordSalt) {
        return new AuthPasswordEncoder(passwordSalt.getBytes());
    }

    @Bean
    public UserRegistrationInput userRegistrationInteractor(PasswordEncoder passwordEncoder,
            MonitoringEventPublisher monitoringEventPublisher,
            UserRegistrationInputDbRepository inputDbRepository) {
        return AuthUserRegistrationInteractor.builder()
                .eventPublisher(monitoringEventPublisher)
                .inputDbRepository(inputDbRepository)
                .passwordEncoder(passwordEncoder)
                .build();
    }
}
