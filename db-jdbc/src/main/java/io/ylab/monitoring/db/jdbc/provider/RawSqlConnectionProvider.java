package io.ylab.monitoring.db.jdbc.provider;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;

import java.sql.Connection;

/**
 * Прокси обертка над обычным объектом соединения
 */
public class RawSqlConnectionProvider implements SqlConnectionProvider {
    private final SqlConnection connection;

    public RawSqlConnectionProvider(Connection connection) {
        this.connection = new RawSqlConnection(connection);
    }

    @Override
    public SqlConnection getConnection() throws JdbcDbException {
        return connection;
    }
}
