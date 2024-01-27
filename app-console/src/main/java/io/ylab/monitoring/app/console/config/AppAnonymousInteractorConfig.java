package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AbstractInteractorConfig;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.Builder;

/**
 * Конфигуратор сценариев для роли анонима
 */
public class AppAnonymousInteractorConfig extends AbstractInteractorConfig {
    private final DatabaseConfig databaseConfig;

    private final MonitoringEventPublisher eventPublisher;

    private final AppInputResponseFactoryConfig responseFactoryConfig;

    @Builder
    public AppAnonymousInteractorConfig(DatabaseConfig databaseConfig, MonitoringEventPublisher eventPublisher,
            AppInputResponseFactoryConfig responseFactoryConfig) {
        this.databaseConfig = databaseConfig;
        this.eventPublisher = eventPublisher;
        this.responseFactoryConfig = responseFactoryConfig;
        init();
    }

    private void init() {
        put(AppCommandName.EXIT, null);

        put(AppCommandName.REGISTRATION, null);
        put(AppCommandName.LOGIN, null);
    }
}
