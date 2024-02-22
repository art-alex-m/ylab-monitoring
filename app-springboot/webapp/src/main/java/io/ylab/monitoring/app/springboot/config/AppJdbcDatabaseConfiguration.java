package io.ylab.monitoring.app.springboot.config;

import io.ylab.monitoring.db.jdbc.provider.DataSourceSqlConnectionProvider;
import io.ylab.monitoring.db.jdbc.provider.SqlConnectionProvider;
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

import javax.sql.DataSource;

@Profile("monitoring-db-jdbc")
@Configuration
public class AppJdbcDatabaseConfiguration {
    @Bean("db")
    public SqlConnectionProvider connection(DataSource dataSource) {
        return new DataSourceSqlConnectionProvider(dataSource);
    }

    @Bean("appSqlQueryRepository")
    public SqlQueryRepository sqlQueryRepository() {
        return new SqlQueryResourcesRepository();
    }

    @Bean("adminMeterReadingsDbRepository")
    public JdbcAdminMeterReadingsDbRepository adminMeterReadingsDbRepository(SqlConnectionProvider connection,
            SqlQueryRepository queryRepository) {
        return new JdbcAdminMeterReadingsDbRepository(queryRepository, connection);
    }

    @Bean("auditLogInputDbRepository")
    public JdbcAuditLogDbRepository auditLogInputDbRepository(SqlConnectionProvider connection,
            SqlQueryRepository queryRepository) {
        return new JdbcAuditLogDbRepository(queryRepository, connection);
    }

    @Bean("userMeterReadingsDbRepository")
    public JdbcUserMeterReadingsDbRepository userMeterReadingsDbRepository(SqlConnectionProvider connection,
            SqlQueryRepository queryRepository) {
        return new JdbcUserMeterReadingsDbRepository(queryRepository, connection);
    }

    @Bean("appMetersDbRepository")
    public JdbcUserMetersDbRepository viewMetersInputDbRepository(SqlConnectionProvider connection,
            SqlQueryRepository queryRepository) {
        return new JdbcUserMetersDbRepository(queryRepository, connection);
    }

    @Bean
    public JdbcAuthUserDbRepository userLoginInputDbRepository(SqlConnectionProvider connection,
            SqlQueryRepository sqlQueryRepository) {
        return new JdbcAuthUserDbRepository(sqlQueryRepository, connection);
    }
}
