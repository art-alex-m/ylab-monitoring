package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AbstractInteractorConfig;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.auth.boundary.AuthUserLoginInteractor;
import io.ylab.monitoring.auth.boundary.AuthUserRegistrationInteractor;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.Builder;

/**
 * Конфигуратор сценариев для роли анонима
 */
public class AppAnonymousInteractorConfig extends AbstractInteractorConfig {
    private final DatabaseConfig databaseConfig;

    private final MonitoringEventPublisher eventPublisher;

    @Builder
    public AppAnonymousInteractorConfig(DatabaseConfig databaseConfig, MonitoringEventPublisher eventPublisher) {
        this.databaseConfig = databaseConfig;
        this.eventPublisher = eventPublisher;
        init();
    }

    private void init() {
        put(AppCommandName.EXIT, null);

        put(AppCommandName.REGISTRATION, new AuthUserRegistrationInteractor(
                databaseConfig.getUserRegistrationInputDbRepository(), eventPublisher));
        put(AppCommandName.LOGIN, new AuthUserLoginInteractor(eventPublisher,
                databaseConfig.getUserLoginInputDbRepository()));
    }
}
