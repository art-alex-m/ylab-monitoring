package io.ylab.monitoring.app.servlets.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.event.DummyMonitoringEventPublisher;
import io.ylab.monitoring.app.servlets.mapper.AuditItemAppAuditItemMapperImpl;
import io.ylab.monitoring.app.servlets.mapper.MeterAppMeterMapperImpl;
import io.ylab.monitoring.app.servlets.mapper.MeterReadingAppMeterReadingMapperImpl;
import io.ylab.monitoring.app.servlets.model.AppDbProperties;
import io.ylab.monitoring.app.servlets.out.AppAuditLogInputResponseFactory;
import io.ylab.monitoring.app.servlets.out.AppMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.servlets.out.AppSubmissionMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.servlets.out.AppViewMetersInputResponseFactory;
import io.ylab.monitoring.app.servlets.service.AppPropertiesLoader;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.audit.boundary.AuditCreateAuditLogInteractor;
import io.ylab.monitoring.audit.boundary.AuditViewAuditLogInteractor;
import io.ylab.monitoring.auth.boundary.AuthUserLoginInteractor;
import io.ylab.monitoring.auth.boundary.AuthUserRegistrationInteractor;
import io.ylab.monitoring.auth.service.AuthPasswordEncoder;
import io.ylab.monitoring.core.boundary.CoreGetActualMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreGetMonthMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreSubmissionMeterReadingsInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMeterReadingsHistoryInteractor;
import io.ylab.monitoring.core.boundary.CoreViewMetersInteractor;
import io.ylab.monitoring.core.service.CorePeriodService;
import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.repository.JdbcAdminMeterReadingsDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcAuditLogDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcAuthUserDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcUserMeterReadingsDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcUserMetersDbRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryResourcesRepository;
import io.ylab.monitoring.domain.audit.boundary.CreateAuditLogInput;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.service.PeriodService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Конфигурация приложения
 */
public class AppConfiguration {

    public final static AppConfiguration REGISTRY = new AppConfiguration();

    private AppConfiguration() {
    }

    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public AppUserContext appUserContext() {
        return new AppUserContext();
    }

    public AuthTokenManager authTokenManager() {
        return new AuthTokenManager();
    }

    public Properties applicationProperties() {
        return AppPropertiesLoader.loadDefault();
    }

    public AppDbProperties appDbProperties() {
        Properties applicationProperties = applicationProperties();
        return new AppDbProperties(applicationProperties.getProperty("ylab.monitoring.db.username"),
                applicationProperties.getProperty("ylab.monitoring.db.password"),
                applicationProperties.getProperty("ylab.monitoring.db.jdbc.url"));
    }

    public Connection connection() {
        AppDbProperties dbProperties = appDbProperties();
        try {
            return DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(),
                    dbProperties.getPassword());
        } catch (SQLException e) {
            throw new JdbcDbException(e);
        }
    }

    public SqlQueryRepository sqlQueryRepository() {
        return new SqlQueryResourcesRepository();
    }

    public JdbcAdminMeterReadingsDbRepository adminMeterReadingsDbRepository() {
        return new JdbcAdminMeterReadingsDbRepository(sqlQueryRepository(), connection());
    }

    public JdbcAuditLogDbRepository auditLogInputDbRepository() {
        return new JdbcAuditLogDbRepository(sqlQueryRepository(), connection());
    }

    public JdbcUserMeterReadingsDbRepository userMeterReadingsDbRepository() {
        return new JdbcUserMeterReadingsDbRepository(sqlQueryRepository(), connection());
    }

    public JdbcUserMetersDbRepository viewMetersInputDbRepository() {
        return new JdbcUserMetersDbRepository(sqlQueryRepository(), connection());
    }

    public JdbcAuthUserDbRepository userLoginInputDbRepository() {
        return new JdbcAuthUserDbRepository(sqlQueryRepository(), connection());
    }

    public MonitoringEventPublisher monitoringEventPublisher() {
        return new DummyMonitoringEventPublisher();
    }

    public PasswordEncoder passwordEncoder() {
        String passwordSalt = applicationProperties().getProperty("ylab.monitoring.auth.password.salt", "");
        return new AuthPasswordEncoder(passwordSalt.getBytes());
    }

    public UserLoginInput userLoginInteractor() {
        return AuthUserLoginInteractor.builder()
                .passwordEncoder(passwordEncoder())
                .eventPublisher(monitoringEventPublisher())
                .inputDbRepository(userLoginInputDbRepository())
                .build();
    }

    public UserRegistrationInput userRegistrationInteractor() {
        return AuthUserRegistrationInteractor.builder()
                .eventPublisher(monitoringEventPublisher())
                .inputDbRepository(userLoginInputDbRepository())
                .passwordEncoder(passwordEncoder())
                .build();
    }

    public AppMeterReadingsInputResponseFactory appMeterReadingsInputResponseFactory() {
        return new AppMeterReadingsInputResponseFactory(new MeterReadingAppMeterReadingMapperImpl());
    }

    public AppAuditLogInputResponseFactory appAuditLogInputResponseFactory() {
        return new AppAuditLogInputResponseFactory(new AuditItemAppAuditItemMapperImpl());
    }

    public AppViewMetersInputResponseFactory appViewMetersInputResponseFactory() {
        return new AppViewMetersInputResponseFactory(new MeterAppMeterMapperImpl());
    }

    public AppSubmissionMeterReadingsInputResponseFactory appSubmissionMeterReadingsInputResponseFactory() {
        return new AppSubmissionMeterReadingsInputResponseFactory(new MeterReadingAppMeterReadingMapperImpl());
    }

    public GetActualMeterReadingsInput adminActualMeterReadingsInteractor() {
        return CoreGetActualMeterReadingsInteractor.builder()
                .eventPublisher(monitoringEventPublisher())
                .responseFactory(appMeterReadingsInputResponseFactory())
                .readingsDbRepository(adminMeterReadingsDbRepository())
                .build();
    }

    public GetMonthMeterReadingsInput adminMonthMeterReadingsInteractor() {
        return CoreGetMonthMeterReadingsInteractor.builder()
                .readingsDbRepository(adminMeterReadingsDbRepository())
                .periodService(periodService())
                .responseFactory(appMeterReadingsInputResponseFactory())
                .eventPublisher(monitoringEventPublisher())
                .build();
    }

    public ViewMeterReadingsHistoryInput adminMeterReadingsHistoryInteractor() {
        return CoreViewMeterReadingsHistoryInteractor.builder()
                .eventPublisher(monitoringEventPublisher())
                .readingsDbRepository(adminMeterReadingsDbRepository())
                .responseFactory(appMeterReadingsInputResponseFactory())
                .build();
    }

    public ViewAuditLogInput adminAuditLogInteractor() {
        return AuditViewAuditLogInteractor.builder()
                .eventPublisher(monitoringEventPublisher())
                .inputDbRepository(auditLogInputDbRepository())
                .responseFactory(appAuditLogInputResponseFactory())
                .build();
    }

    public CreateAuditLogInput createAuditLogInteractor() {
        return new AuditCreateAuditLogInteractor(auditLogInputDbRepository());
    }


    public ViewMetersInput viewMetersInteractor() {
        return CoreViewMetersInteractor.builder()
                .metersDbRepository(viewMetersInputDbRepository())
                .responseFactory(appViewMetersInputResponseFactory())
                .eventPublisher(monitoringEventPublisher())
                .build();
    }

    public GetActualMeterReadingsInput actualMeterReadingsInteracror() {
        return CoreGetActualMeterReadingsInteractor.builder()
                .eventPublisher(monitoringEventPublisher())
                .responseFactory(appMeterReadingsInputResponseFactory())
                .readingsDbRepository(userMeterReadingsDbRepository())
                .build();
    }

    public GetMonthMeterReadingsInput monthMeterReadingsInteractor() {
        return CoreGetMonthMeterReadingsInteractor.builder()
                .readingsDbRepository(userMeterReadingsDbRepository())
                .periodService(periodService())
                .responseFactory(appMeterReadingsInputResponseFactory())
                .eventPublisher(monitoringEventPublisher())
                .build();
    }

    public ViewMeterReadingsHistoryInput meterReadingsHistoryInteractor() {
        return CoreViewMeterReadingsHistoryInteractor.builder()
                .eventPublisher(monitoringEventPublisher())
                .readingsDbRepository(userMeterReadingsDbRepository())
                .responseFactory(appMeterReadingsInputResponseFactory())
                .build();
    }

    public SubmissionMeterReadingsInput submissionMeterReadingsInteractor() {
        return CoreSubmissionMeterReadingsInteractor.builder()
                .responseFactory(appSubmissionMeterReadingsInputResponseFactory())
                .meterDbRepository(viewMetersInputDbRepository())
                .readingsDbRepository(userMeterReadingsDbRepository())
                .periodService(periodService())
                .eventPublisher(monitoringEventPublisher())
                .build();
    }

    public PeriodService periodService() {
        return new CorePeriodService();
    }
}
