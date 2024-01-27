package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AbstractInteractorConfig;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.core.boundary.*;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.service.PeriodService;
import lombok.Builder;

/**
 * Конфигуратор сценариев для роли обычного пользователя
 */
public class AppUserInteractorConfig extends AbstractInteractorConfig {
    private final DatabaseConfig databaseConfig;

    private final MonitoringEventPublisher eventPublisher;

    private final AppInputResponseFactoryConfig responseFactoryConfig;

    private final PeriodService periodService;

    @Builder
    public AppUserInteractorConfig(DatabaseConfig databaseConfig, MonitoringEventPublisher eventPublisher,
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

        put(AppCommandName.METERS_LIST, new CoreViewMetersInteractor(
                responseFactoryConfig.getViewMetersInputResponseFactory(),
                databaseConfig.getViewMetersInputDbRepository(), eventPublisher));

        put(AppCommandName.READING_SUBMIT, CoreSubmissionMeterReadingsInteractor.builder()
                .eventPublisher(eventPublisher)
                .meterDbRepository(databaseConfig.getSubmissionMeterReadingsInputMeterDbRepository())
                .periodService(periodService)
                .readingsDbRepository(databaseConfig.getSubmissionMeterReadingsInputDbRepository())
                .build()
        );

        put(AppCommandName.READING_ACTUAL, new CoreGetActualMeterReadingsInteractor(
                responseFactoryConfig.getActualMeterReadingsInputResponseFactory(),
                databaseConfig.getUserActualMeterReadingsInputDbRepository(), eventPublisher));

        put(AppCommandName.READING_MONTH, CoreGetMonthMeterReadingsInteractor.builder()
                .eventPublisher(eventPublisher)
                .periodService(periodService)
                .responseFactory(responseFactoryConfig.getMonthMeterReadingsResponseFactory())
                .readingsDbRepository(databaseConfig.getUserMonthMeterReadingsInputDbRepository())
                .build());

        put(AppCommandName.READING_HISTORY, new CoreViewMeterReadingsHistoryInteractor(
                responseFactoryConfig.getViewMeterReadingsHistoryInputResponseFactory(),
                databaseConfig.getUserViewMeterReadingsHistoryInputDbRepository(), eventPublisher));
    }
}
