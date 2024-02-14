package io.ylab.monitoring.app.springmvc.config;


import io.ylab.monitoring.app.springmvc.out.AppAuditLogInputResponseFactory;
import io.ylab.monitoring.app.springmvc.out.AppMeterReadingsInputResponseFactory;
import io.ylab.monitoring.audit.boundary.AuditCreateAuditLogInteractor;
import io.ylab.monitoring.audit.boundary.AuditViewAuditLogInteractor;
import io.ylab.monitoring.core.boundary.CoreGetActualMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreGetMonthMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMeterReadingsHistoryInteractor;
import io.ylab.monitoring.core.service.CorePeriodService;
import io.ylab.monitoring.db.jdbc.repository.JdbcAdminMeterReadingsDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcAuditLogDbRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

/**
 * Конфигурация сценариев работы с показаниями счетчиков для администратора
 */
@Configuration
public class AdminConfiguration {
    @Bean("adminActualMeterReadingsInteractor")
    public GetActualMeterReadingsInput actualMeterReadingsInteractor(
            @Qualifier("adminMeterReadingsDbRepository") GetActualMeterReadingsInputDbRepository inputDbRepository,
            MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {

        return CoreGetActualMeterReadingsInteractor.builder()
                .eventPublisher(eventPublisher)
                .responseFactory(responseFactory)
                .readingsDbRepository(inputDbRepository)
                .build();
    }

    @Bean("adminMonthMeterReadingsInteractor")
    public GetMonthMeterReadingsInput monthMeterReadingsInteractor(
            @Qualifier("adminMeterReadingsDbRepository") GetMonthMeterReadingsInputDbRepository inputDbRepository,
            MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {

        return CoreGetMonthMeterReadingsInteractor.builder()
                .readingsDbRepository(inputDbRepository)
                .periodService(new CorePeriodService())
                .responseFactory(responseFactory)
                .eventPublisher(eventPublisher)
                .build();
    }

    @Bean("adminMeterReadingsHistoryInteractor")
    public ViewMeterReadingsHistoryInput meterReadingsHistoryInteractor(
            @Qualifier("adminMeterReadingsDbRepository") ViewMeterReadingsHistoryInputDbRepository inputDbRepository,
            MonitoringEventPublisher eventPublisher,
            AppMeterReadingsInputResponseFactory responseFactory) {

        return CoreViewMeterReadingsHistoryInteractor.builder()
                .eventPublisher(eventPublisher)
                .readingsDbRepository(inputDbRepository)
                .responseFactory(responseFactory)
                .build();
    }

    @Bean("adminMeterReadingsDbRepository")
    public JdbcAdminMeterReadingsDbRepository adminMeterReadingsDbRepository(Connection connection,
            SqlQueryRepository queryRepository) {
        return new JdbcAdminMeterReadingsDbRepository(queryRepository, connection);
    }

    @Bean("auditLogInputDbRepository")
    public JdbcAuditLogDbRepository auditLogInputDbRepository(Connection connection,
            SqlQueryRepository queryRepository) {
        return new JdbcAuditLogDbRepository(queryRepository, connection);
    }

    @Bean
    public ViewAuditLogInput auditLogInteractor(MonitoringEventPublisher eventPublisher,
            ViewAuditLogInputDbRepository auditLogInputDbRepository,
            AppAuditLogInputResponseFactory responseFactory) {

        return AuditViewAuditLogInteractor.builder()
                .eventPublisher(eventPublisher)
                .inputDbRepository(auditLogInputDbRepository)
                .responseFactory(responseFactory)
                .build();
    }

    @Bean
    public CreateAuditLogInput createAuditLogInteractor(CreateAuditLogInputDbRepository auditLogInputDbRepository) {
        return new AuditCreateAuditLogInteractor(auditLogInputDbRepository);
    }
}
