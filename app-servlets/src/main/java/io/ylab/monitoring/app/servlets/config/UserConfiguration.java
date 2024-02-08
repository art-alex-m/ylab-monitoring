package io.ylab.monitoring.app.servlets.config;

import io.ylab.monitoring.app.servlets.out.AppMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.servlets.out.AppSubmissionMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.servlets.out.AppViewMetersInputResponseFactory;
import io.ylab.monitoring.core.boundary.CoreGetActualMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreGetMonthMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreSubmissionMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMeterReadingsHistoryInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMetersInteractor;
import io.ylab.monitoring.core.service.CorePeriodService;
import io.ylab.monitoring.db.jdbc.repository.JdbcUserMeterReadingsDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcUserMetersDbRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;
import io.ylab.monitoring.domain.core.service.PeriodService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.sql.Connection;

/**
 * Конфигурация сценариев для пользователя
 */
@ApplicationScoped
public class UserConfiguration {

    @Produces
    @Singleton
    @Named("appUserViewMetersInput")
    public ViewMetersInput viewMetersInteractor(
            @Named("appMetersDbRepository") ViewMetersInputDbRepository dbRepository,
            AppViewMetersInputResponseFactory responseFactory,
            @Named("appEventPublisher") MonitoringEventPublisher eventPublisher) {

        return CoreViewMetersInteractor.builder()
                .metersDbRepository(dbRepository)
                .responseFactory(responseFactory)
                .eventPublisher(eventPublisher)
                .build();
    }

    @Produces
    @Singleton
    public GetActualMeterReadingsInput actualMeterReadingsInteracror(
            @Named("userMeterReadingsDbRepository") GetActualMeterReadingsInputDbRepository inputDbRepository,
            @Named("appEventPublisher") MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {

        return CoreGetActualMeterReadingsInteractor.builder()
                .eventPublisher(eventPublisher)
                .responseFactory(responseFactory)
                .readingsDbRepository(inputDbRepository)
                .build();
    }

    @Produces
    @Singleton
    public GetMonthMeterReadingsInput monthMeterReadingsInteractor(
            @Named("userMeterReadingsDbRepository") GetMonthMeterReadingsInputDbRepository inputDbRepository,
            @Named("appEventPublisher") MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {
        return CoreGetMonthMeterReadingsInteractor.builder()
                .readingsDbRepository(inputDbRepository)
                .periodService(new CorePeriodService())
                .responseFactory(responseFactory)
                .eventPublisher(eventPublisher)
                .build();
    }

    @Produces
    @Singleton
    public ViewMeterReadingsHistoryInput meterReadingsHistoryInteractor(
            @Named("userMeterReadingsDbRepository") ViewMeterReadingsHistoryInputDbRepository inputDbRepository,
            @Named("appEventPublisher") MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {

        return CoreViewMeterReadingsHistoryInteractor.builder()
                .eventPublisher(eventPublisher)
                .readingsDbRepository(inputDbRepository)
                .responseFactory(responseFactory)
                .build();
    }

    @Produces
    @Singleton
    public SubmissionMeterReadingsInput submissionMeterReadingsInteractor(
            @Named("userMeterReadingsDbRepository") SubmissionMeterReadingsInputDbRepository inputDbRepository,
            @Named("appEventPublisher") MonitoringEventPublisher eventPublisher, PeriodService periodService,
            @Named("appMetersDbRepository") SubmissionMeterReadingsInputMeterDbRepository metersInputDbRepository,
            AppSubmissionMeterReadingsInputResponseFactory responseFactory) {

        return CoreSubmissionMeterReadingsInteractor.builder()
                .responseFactory(responseFactory)
                .meterDbRepository(metersInputDbRepository)
                .readingsDbRepository(inputDbRepository)
                .periodService(periodService)
                .eventPublisher(eventPublisher)
                .build();
    }

    @Produces
    @Singleton
    @Named("userMeterReadingsDbRepository")
    public JdbcUserMeterReadingsDbRepository userMeterReadingsDbRepository(@Named("db") Connection connection,
            @Named("appSqlQueryRepository") SqlQueryRepository queryRepository) {
        return new JdbcUserMeterReadingsDbRepository(queryRepository, connection);
    }

    @Produces
    @Singleton
    @Named("appMetersDbRepository")
    public JdbcUserMetersDbRepository viewMetersInputDbRepository(@Named("db") Connection connection,
            @Named("appSqlQueryRepository") SqlQueryRepository queryRepository) {
        return new JdbcUserMetersDbRepository(queryRepository, connection);
    }

    @Produces
    @Singleton
    public PeriodService periodService() {
        return new CorePeriodService();
    }
}
