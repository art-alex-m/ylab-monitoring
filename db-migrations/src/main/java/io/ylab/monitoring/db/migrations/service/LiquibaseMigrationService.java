package io.ylab.monitoring.db.migrations.service;

import io.ylab.monitoring.db.migrations.exception.DatabaseMigrationException;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Запускает миграции базы данных
 */
@RequiredArgsConstructor
@Builder
public class LiquibaseMigrationService {

    public static final String CHANGELOG_MASTER_FILE = "db/changelog/db.changelog-master.yaml";

    private final String url;

    private final String username;

    private final String password;

    /**
     * Вызов обновления базы данных
     *
     * @param contexts Список контекстов для миграций
     */
    public void migrate(String... contexts) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase(CHANGELOG_MASTER_FILE, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(contexts), new PrintWriter(System.out));
        } catch (SQLException | LiquibaseException ex) {
            throw new DatabaseMigrationException(ex);
        }
    }
}
