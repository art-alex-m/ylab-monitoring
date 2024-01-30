package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AbstractInteractorConfig;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.auth.boundary.AuthUserLogoutInteractor;
import io.ylab.monitoring.core.boundary.*;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;
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
            AppInputResponseFactoryConfig responseFactoryConfig, PeriodService periodService,
            PasswordEncoder passwordEncoder) {
        this.databaseConfig = databaseConfig;
        this.eventPublisher = eventPublisher;
        this.responseFactoryConfig = responseFactoryConfig;
        this.periodService = periodService;
        init();
    }

    private void init() {
        put(AppCommandName.EXIT, null);
        put(AppCommandName.LOGOUT, new AuthUserLogoutInteractor(eventPublisher));

        put(AppCommandName.METER_LIST, new CoreViewMetersInteractor(
                responseFactoryConfig.getViewMetersInputResponseFactory(),
                databaseConfig.getViewMetersInputDbRepository(), eventPublisher));

        SubmissionMeterReadingsInput submissionInteractor = CoreSubmissionMeterReadingsInteractor.builder()
                .eventPublisher(eventPublisher)
                .meterDbRepository(databaseConfig.getSubmissionMeterReadingsInputMeterDbRepository())
                .periodService(periodService)
                .readingsDbRepository(databaseConfig.getSubmissionMeterReadingsInputDbRepository())
                .responseFactory(responseFactoryConfig.getSubmissionMeterReadingsInputResponseFactory())
                .build();

        put(AppCommandName.READING_SUBMIT, submissionInteractor);
        put(AppCommandName.READING_SUBMIT_EXT, submissionInteractor);

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
