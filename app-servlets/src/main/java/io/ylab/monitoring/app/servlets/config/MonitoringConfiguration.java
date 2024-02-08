package io.ylab.monitoring.app.servlets.config;

import io.ylab.monitoring.app.servlets.event.DummyMonitoringEventPublisher;
import io.ylab.monitoring.app.servlets.service.AppPropertiesLoader;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryResourcesRepository;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Базовая конфигурация приложения
 */
@ApplicationScoped
public class MonitoringConfiguration {

    @Produces
    @Singleton
    @Named("appProperties")
    public Properties applicationProperties() {
        return AppPropertiesLoader.loadDefault();
    }

    @Produces
    @Singleton
    @Named("db")
    public Connection connection(@Named("appProperties") Properties applicationProperties) throws SQLException {
        String url = applicationProperties.getProperty("ylab.monitoring.db.jdbc.url");
        String username = applicationProperties.getProperty("ylab.monitoring.db.username");
        String password = applicationProperties.getProperty("ylab.monitoring.db.password");
        return DriverManager.getConnection(url, username, password);
    }

    @Produces
    @Singleton
    @Named("appEventPublisher")
    public MonitoringEventPublisher monitoringEventPublisher() {
        return new DummyMonitoringEventPublisher();
    }

    @Produces
    @Singleton
    @Named("appSqlQueryRepository")
    public SqlQueryRepository sqlQueryRepository() {
        return new SqlQueryResourcesRepository();
    }
}
