package io.ylab.monitoring.app.servlets.config;

import io.ylab.monitoring.auth.boundary.AuthUserLoginInteractor;
import io.ylab.monitoring.auth.boundary.AuthUserRegistrationInteractor;
import io.ylab.monitoring.auth.service.AuthPasswordEncoder;
import io.ylab.monitoring.db.jdbc.repository.JdbcAuthUserDbRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.sql.Connection;
import java.util.Properties;

/**
 * Конфигурация сценариев регистрации, входа-выхода
 */
@ApplicationScoped
public class AuthConfiguration {

    @Produces
    @Singleton
    public UserLoginInput userLoginInteractor(PasswordEncoder passwordEncoder,
            @Named("appEventPublisher") MonitoringEventPublisher monitoringEventPublisher,
            UserLoginInputDbRepository inputDbRepository) {

        return AuthUserLoginInteractor.builder()
                .passwordEncoder(passwordEncoder)
                .eventPublisher(monitoringEventPublisher)
                .inputDbRepository(inputDbRepository)
                .build();
    }

    @Produces
    @Singleton
    public PasswordEncoder passwordEncoder(@Named("appProperties") Properties applicationProperties) {
        return new AuthPasswordEncoder(applicationProperties
                .getProperty("ylab.monitoring.auth.password.salt").getBytes());
    }


    @Produces
    @Singleton
    public JdbcAuthUserDbRepository userLoginInputDbRepository(Connection connection,
            @Named("appSqlQueryRepository") SqlQueryRepository sqlQueryRepository) {
        return new JdbcAuthUserDbRepository(sqlQueryRepository, connection);
    }

    @Produces
    @Singleton
    public UserRegistrationInput userRegistrationInteractor(PasswordEncoder passwordEncoder,
            @Named("appEventPublisher") MonitoringEventPublisher monitoringEventPublisher,
            UserRegistrationInputDbRepository inputDbRepository) {

        return AuthUserRegistrationInteractor.builder()
                .eventPublisher(monitoringEventPublisher)
                .inputDbRepository(inputDbRepository)
                .passwordEncoder(passwordEncoder)
                .build();
    }
}
