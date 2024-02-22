package io.ylab.monitoring.db.jdbc.provider;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import lombok.AllArgsConstructor;

import java.sql.Connection;

/**
 * Прокси обертка над обычным объектом соединения
 */
@AllArgsConstructor
public class RawSqlConnectionProvider implements SqlConnectionProvider {
    private final Connection connection;

    @Override
    public SqlConnection getConnection() throws JdbcDbException {
        return new RawSqlConnection(connection);
    }
}
