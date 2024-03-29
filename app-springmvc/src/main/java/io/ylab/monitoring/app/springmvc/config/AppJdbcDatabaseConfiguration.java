package io.ylab.monitoring.app.springmvc.config;

import io.ylab.monitoring.app.springmvc.model.AppDbProperties;
import io.ylab.monitoring.db.jdbc.repository.JdbcAdminMeterReadingsDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcAuditLogDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcAuthUserDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcUserMeterReadingsDbRepository;
import io.ylab.monitoring.db.jdbc.repository.JdbcUserMetersDbRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryResourcesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Profile("monitoring-db-jdbc")
@Configuration
public class AppJdbcDatabaseConfiguration {
    @Bean("db")
    public Connection connection(AppDbProperties dbProperties) throws SQLException {
        return DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(),
                dbProperties.getPassword());
    }

    @Bean("appSqlQueryRepository")
    public SqlQueryRepository sqlQueryRepository() {
        return new SqlQueryResourcesRepository();
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

    @Bean("userMeterReadingsDbRepository")
    public JdbcUserMeterReadingsDbRepository userMeterReadingsDbRepository(Connection connection,
            SqlQueryRepository queryRepository) {
        return new JdbcUserMeterReadingsDbRepository(queryRepository, connection);
    }

    @Bean("appMetersDbRepository")
    public JdbcUserMetersDbRepository viewMetersInputDbRepository(Connection connection,
            SqlQueryRepository queryRepository) {
        return new JdbcUserMetersDbRepository(queryRepository, connection);
    }

    @Bean
    public JdbcAuthUserDbRepository userLoginInputDbRepository(Connection connection,
            SqlQueryRepository sqlQueryRepository) {
        return new JdbcAuthUserDbRepository(sqlQueryRepository, connection);
    }
}
