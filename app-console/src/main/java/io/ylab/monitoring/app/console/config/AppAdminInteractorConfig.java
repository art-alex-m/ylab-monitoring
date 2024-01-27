package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AbstractInteractorConfig;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.core.boundary.CoreGetActualMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreGetMonthMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMeterReadingsHistoryInteractor;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.service.PeriodService;
import lombok.Builder;

/**
 * Конфигуратор сценариев для роли администратора
 */
public class AppAdminInteractorConfig extends AbstractInteractorConfig {
    private final DatabaseConfig databaseConfig;

    private final MonitoringEventPublisher eventPublisher;

    private final AppInputResponseFactoryConfig responseFactoryConfig;

    private final PeriodService periodService;

    @Builder
    public AppAdminInteractorConfig(DatabaseConfig databaseConfig, MonitoringEventPublisher eventPublisher,
            AppInputResponseFactoryConfig responseFactoryConfig, PeriodService periodService) {
        this.databaseConfig = databaseConfig;
        this.eventPublisher = eventPublisher;
        this.responseFactoryConfig = responseFactoryConfig;
        this.periodService = periodService;
        init();
    }

    private void init() {
        put(AppCommandName.EXIT, null);
        put(AppCommandName.LOGOUT, null);

        put(AppCommandName.READING_ACTUAL, new CoreGetActualMeterReadingsInteractor(
                responseFactoryConfig.getActualMeterReadingsInputResponseFactory(),
                databaseConfig.getAdminActualMeterReadingsInputDbRepository(), eventPublisher));

        put(AppCommandName.READING_MONTH, CoreGetMonthMeterReadingsInteractor.builder()
                .eventPublisher(eventPublisher)
                .periodService(periodService)
                .responseFactory(responseFactoryConfig.getMonthMeterReadingsResponseFactory())
                .readingsDbRepository(databaseConfig.getAdminMonthMeterReadingsInputDbRepository())
                .build());

        put(AppCommandName.READING_HISTORY, new CoreViewMeterReadingsHistoryInteractor(
                responseFactoryConfig.getViewMeterReadingsHistoryInputResponseFactory(),
                databaseConfig.getAdminViewMeterReadingsHistoryInputDbRepository(), eventPublisher));
    }
}
