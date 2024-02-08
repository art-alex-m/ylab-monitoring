package io.ylab.monitoring.app.servlets.config;

import io.ylab.monitoring.app.servlets.out.AppAuditLogInputResponseFactory;
import io.ylab.monitoring.app.servlets.out.AppMeterReadingsInputResponseFactory;
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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.sql.Connection;

/**
 * Конфигурация сценариев работы с показаниями счетчиков для администратора
 */
@ApplicationScoped
public class AdminConfiguration {
    @Produces
    @Singleton
    @Named("adminActualMeterReadingsInteractor")
    public GetActualMeterReadingsInput actualMeterReadingsInteractor(
            @Named("adminMeterReadingsDbRepository") GetActualMeterReadingsInputDbRepository inputDbRepository,
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
    @Named("adminMonthMeterReadingsInteractor")
    public GetMonthMeterReadingsInput monthMeterReadingsInteractor(
            @Named("adminMeterReadingsDbRepository") GetMonthMeterReadingsInputDbRepository inputDbRepository,
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
    @Named("adminMeterReadingsHistoryInteractor")
    public ViewMeterReadingsHistoryInput meterReadingsHistoryInteractor(
            @Named("adminMeterReadingsDbRepository") ViewMeterReadingsHistoryInputDbRepository inputDbRepository,
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
    @Named("adminMeterReadingsDbRepository")
    public JdbcAdminMeterReadingsDbRepository adminMeterReadingsDbRepository(@Named("db") Connection connection,
            @Named("appSqlQueryRepository") SqlQueryRepository queryRepository) {
        return new JdbcAdminMeterReadingsDbRepository(queryRepository, connection);
    }

    @Produces
    @Singleton
    @Named("auditLogInputDbRepository")
    public JdbcAuditLogDbRepository auditLogInputDbRepository(@Named("db") Connection connection,
            @Named("appSqlQueryRepository") SqlQueryRepository queryRepository) {
        return new JdbcAuditLogDbRepository(queryRepository, connection);
    }

    @Produces
    @Singleton
    public ViewAuditLogInput auditLogInteractor(
            @Named("appEventPublisher") MonitoringEventPublisher eventPublisher,
            @Named("auditLogInputDbRepository") ViewAuditLogInputDbRepository auditLogInputDbRepository,
            AppAuditLogInputResponseFactory responseFactory) {

        return AuditViewAuditLogInteractor.builder()
                .eventPublisher(eventPublisher)
                .inputDbRepository(auditLogInputDbRepository)
                .responseFactory(responseFactory)
                .build();
    }

    @Produces
    @Singleton
    public CreateAuditLogInput createAuditLogInteractor(
            @Named("auditLogInputDbRepository") CreateAuditLogInputDbRepository auditLogInputDbRepository) {
        return new AuditCreateAuditLogInteractor(auditLogInputDbRepository);
    }
}
