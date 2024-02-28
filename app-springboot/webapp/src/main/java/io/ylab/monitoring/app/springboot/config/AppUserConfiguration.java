package io.ylab.monitoring.app.springboot.config;

import io.ylab.monitoring.app.springboot.out.AppMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.springboot.out.AppSubmissionMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.springboot.out.AppViewMetersInputResponseFactory;
import io.ylab.monitoring.core.boundary.CoreGetActualMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreGetMonthMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreSubmissionMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMeterReadingsHistoryInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMetersInteractor;
import io.ylab.monitoring.core.service.CorePeriodService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация сценариев для пользователя
 */
@Configuration
public class AppUserConfiguration {

    @Bean("appUserViewMetersInput")
    public ViewMetersInput viewMetersInteractor(
            ViewMetersInputDbRepository dbRepository,
            AppViewMetersInputResponseFactory responseFactory,
            MonitoringEventPublisher eventPublisher) {

        return CoreViewMetersInteractor.builder()
                .metersDbRepository(dbRepository)
                .responseFactory(responseFactory)
                .eventPublisher(eventPublisher)
                .build();
    }

    @Bean("actualMeterReadingsInteracror")
    public GetActualMeterReadingsInput actualMeterReadingsInteracror(
            @Qualifier("userMeterReadingsDbRepository") GetActualMeterReadingsInputDbRepository inputDbRepository,
            MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {

        return CoreGetActualMeterReadingsInteractor.builder()
                .eventPublisher(eventPublisher)
                .responseFactory(responseFactory)
                .readingsDbRepository(inputDbRepository)
                .build();
    }

    @Bean("monthMeterReadingsInteractor")
    public GetMonthMeterReadingsInput monthMeterReadingsInteractor(
            @Qualifier("userMeterReadingsDbRepository") GetMonthMeterReadingsInputDbRepository inputDbRepository,
            MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {
        return CoreGetMonthMeterReadingsInteractor.builder()
                .readingsDbRepository(inputDbRepository)
                .periodService(new CorePeriodService())
                .responseFactory(responseFactory)
                .eventPublisher(eventPublisher)
                .build();
    }

    @Bean("meterReadingsHistoryInteractor")
    public ViewMeterReadingsHistoryInput meterReadingsHistoryInteractor(
            @Qualifier("userMeterReadingsDbRepository") ViewMeterReadingsHistoryInputDbRepository inputDbRepository,
            MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {

        return CoreViewMeterReadingsHistoryInteractor.builder()
                .eventPublisher(eventPublisher)
                .readingsDbRepository(inputDbRepository)
                .responseFactory(responseFactory)
                .build();
    }

    @Bean("submissionMeterReadingsInteractor")
    public SubmissionMeterReadingsInput submissionMeterReadingsInteractor(
            @Qualifier("userMeterReadingsDbRepository") SubmissionMeterReadingsInputDbRepository inputDbRepository,
            MonitoringEventPublisher eventPublisher, PeriodService periodService,
            @Qualifier("appMetersDbRepository") SubmissionMeterReadingsInputMeterDbRepository metersInputDbRepository,
            AppSubmissionMeterReadingsInputResponseFactory responseFactory) {

        return CoreSubmissionMeterReadingsInteractor.builder()
                .responseFactory(responseFactory)
                .meterDbRepository(metersInputDbRepository)
                .readingsDbRepository(inputDbRepository)
                .periodService(periodService)
                .eventPublisher(eventPublisher)
                .build();
    }

    @Bean
    public PeriodService periodService() {
        return new CorePeriodService();
    }
}
