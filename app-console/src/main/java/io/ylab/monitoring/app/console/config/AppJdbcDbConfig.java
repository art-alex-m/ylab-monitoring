package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.exception.AppConfigurationException;
import io.ylab.monitoring.app.console.model.DatabaseConfig;
import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.repository.*;
import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@Getter
public class AppJdbcDbConfig implements DatabaseConfig {
    @Getter(AccessLevel.PRIVATE)
    private final String url;

    @Getter(AccessLevel.PRIVATE)
    private final String username;

    @Getter(AccessLevel.PRIVATE)
    private final String password;

    private GetActualMeterReadingsInputDbRepository userActualMeterReadingsInputDbRepository;
    private GetMonthMeterReadingsInputDbRepository userMonthMeterReadingsInputDbRepository;
    private ViewMeterReadingsHistoryInputDbRepository userViewMeterReadingsHistoryInputDbRepository;
    private GetActualMeterReadingsInputDbRepository adminActualMeterReadingsInputDbRepository;
    private GetMonthMeterReadingsInputDbRepository adminMonthMeterReadingsInputDbRepository;
    private ViewMeterReadingsHistoryInputDbRepository adminViewMeterReadingsHistoryInputDbRepository;
    private SubmissionMeterReadingsInputMeterDbRepository submissionMeterReadingsInputMeterDbRepository;
    private SubmissionMeterReadingsInputDbRepository submissionMeterReadingsInputDbRepository;
    private ViewMetersInputDbRepository viewMetersInputDbRepository;
    private UserLoginInputDbRepository userLoginInputDbRepository;
    private UserRegistrationInputDbRepository userRegistrationInputDbRepository;
    private CreateAuditLogInputDbRepository createAuditLogInputDbRepository;
    private ViewAuditLogInputDbRepository viewAuditLogInputDbRepository;

    @Getter(AccessLevel.PRIVATE)
    private JdbcUserMetersDbRepository jdbcUserMetersDbRepository;

    @Builder
    public AppJdbcDbConfig(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        init();
    }

    /**
     * {@inheritDoc}
     */
    public DatabaseConfig setMeters(List<Meter> meterList) {
        for (Meter meter : meterList) {
            try {
                jdbcUserMetersDbRepository.store(meter);
            } catch (JdbcDbException ignored) {

            }
        }
        return this;
    }

    private void init() {
        Connection connection;
        SqlQueryRepository queryRepository;
        try {
            connection = DriverManager.getConnection(url, username, password);
            queryRepository = new SqlQueryResourcesRepository();
        } catch (Exception ex) {
            throw new AppConfigurationException(ex);
        }

        JdbcAdminMeterReadingsDbRepository jdbcAdminMeterReadingsDbRepository =
                new JdbcAdminMeterReadingsDbRepository(queryRepository, connection);
        JdbcUserMeterReadingsDbRepository jdbcUserMeterReadingsDbRepository =
                new JdbcUserMeterReadingsDbRepository(queryRepository, connection);
        JdbcAuditLogDbRepository jdbcAuditLogDbRepository = new JdbcAuditLogDbRepository(queryRepository, connection);
        JdbcAuthUserDbRepository jdbcAuthUserDbRepository = new JdbcAuthUserDbRepository(queryRepository, connection);
        jdbcUserMetersDbRepository = new JdbcUserMetersDbRepository(queryRepository, connection);

        userActualMeterReadingsInputDbRepository = jdbcUserMeterReadingsDbRepository;
        userMonthMeterReadingsInputDbRepository = jdbcUserMeterReadingsDbRepository;
        userViewMeterReadingsHistoryInputDbRepository = jdbcUserMeterReadingsDbRepository;
        adminActualMeterReadingsInputDbRepository = jdbcAdminMeterReadingsDbRepository;
        adminMonthMeterReadingsInputDbRepository = jdbcAdminMeterReadingsDbRepository;
        adminViewMeterReadingsHistoryInputDbRepository = jdbcAdminMeterReadingsDbRepository;
        submissionMeterReadingsInputDbRepository = jdbcUserMeterReadingsDbRepository;
        submissionMeterReadingsInputMeterDbRepository = jdbcUserMetersDbRepository;
        viewMetersInputDbRepository = jdbcUserMetersDbRepository;
        userLoginInputDbRepository = jdbcAuthUserDbRepository;
        userRegistrationInputDbRepository = jdbcAuthUserDbRepository;
        createAuditLogInputDbRepository = jdbcAuditLogDbRepository;
        viewAuditLogInputDbRepository = jdbcAuditLogDbRepository;
    }
}
