package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.provider.RawSqlConnectionProvider;
import io.ylab.monitoring.db.jdbc.provider.SqlConnection;
import io.ylab.monitoring.db.jdbc.provider.SqlConnectionProvider;

import java.sql.Connection;

/**
 * Базовый класс для репозиториев
 */
public abstract class AbstractDbRepository {

    protected final SqlQueryRepository queryRepository;

    private final SqlConnectionProvider connectionProvider;

    public AbstractDbRepository(SqlQueryRepository queryRepository, SqlConnectionProvider connectionProvider) {
        this.queryRepository = queryRepository;
        this.connectionProvider = connectionProvider;
    }

    public AbstractDbRepository(SqlQueryRepository queryRepository, Connection connection) {
        this(queryRepository, new RawSqlConnectionProvider(connection));
    }

    protected SqlConnection getConnection() {
        return connectionProvider.getConnection();
    }
}
