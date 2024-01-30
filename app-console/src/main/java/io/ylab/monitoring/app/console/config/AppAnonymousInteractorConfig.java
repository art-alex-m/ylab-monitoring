package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AbstractInteractorConfig;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.auth.boundary.AuthUserLoginInteractor;
import io.ylab.monitoring.auth.boundary.AuthUserRegistrationInteractor;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.Builder;

/**
 * Конфигуратор сценариев для роли анонима
 */
public class AppAnonymousInteractorConfig extends AbstractInteractorConfig {
    private final DatabaseConfig databaseConfig;

    private final MonitoringEventPublisher eventPublisher;

    private final PasswordEncoder passwordEncoder;

    @Builder
    public AppAnonymousInteractorConfig(DatabaseConfig databaseConfig, MonitoringEventPublisher eventPublisher,
            PasswordEncoder passwordEncoder) {
        this.databaseConfig = databaseConfig;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
        init();
    }

    private void init() {
        put(AppCommandName.EXIT, null);

        put(AppCommandName.LOGIN, new AuthUserLoginInteractor(eventPublisher,
                databaseConfig.getUserLoginInputDbRepository(), passwordEncoder));
        put(AppCommandName.REGISTRATION, new AuthUserRegistrationInteractor(
                databaseConfig.getUserRegistrationInputDbRepository(), eventPublisher, passwordEncoder));
    }
}
