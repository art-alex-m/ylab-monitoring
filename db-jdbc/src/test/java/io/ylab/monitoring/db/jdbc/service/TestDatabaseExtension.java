package io.ylab.monitoring.db.jdbc.service;

import io.ylab.monitoring.db.migrations.service.LiquibaseMigrationService;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

/**
 * Расширение для организации тестирования с временной БД
 *
 * <p>
 * <code><pre>
 * {@code @ExtendWith(TestDatabaseExtension.class)}
 * class JdbcAuthUserDbRepositoryTest {
 *     {@code @TestConnection}
 *     private Connection connection;
 * }
 * </pre></code>
 * </p>
 */
public class TestDatabaseExtension implements BeforeAllCallback, AfterAllCallback, TestInstancePostProcessor {

    private final static String POSTGRESQL_DOCKER_TAG = "postgres:14.2-alpine";

    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            DockerImageName.parse(POSTGRESQL_DOCKER_TAG));

    private Connection testConnection;

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        try {
            testConnection.close();
            postgreSQLContainer.stop();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        postgreSQLContainer.start();
        LiquibaseMigrationService.builder()
                .url(postgreSQLContainer.getJdbcUrl())
                .password(postgreSQLContainer.getPassword())
                .username(postgreSQLContainer.getUsername())
                .build().migrate();

        testConnection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl() + "&currentSchema=monitoring",
                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws Exception {
        Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TestConnection.class))
                .forEach(field -> {
                    boolean accessible = field.canAccess(testInstance);
                    try {
                        field.setAccessible(true);
                        field.set(testInstance, testConnection);
                        field.setAccessible(accessible);
                    } catch (IllegalAccessException ignored) {
                    }
                });
    }
}
