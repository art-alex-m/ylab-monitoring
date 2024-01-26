package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.Builder;

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
        put(AppCommandName.HELP, null);

        put(AppCommandName.REGISTRATION, null);
        put(AppCommandName.LOGIN, null);
    }
}
