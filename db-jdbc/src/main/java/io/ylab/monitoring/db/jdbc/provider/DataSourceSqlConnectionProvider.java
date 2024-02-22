package io.ylab.monitoring.db.jdbc.provider;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Провайдер подключений к БД на основе javax.sql.DataSource
 */
@AllArgsConstructor
public class DataSourceSqlConnectionProvider implements SqlConnectionProvider {

    private final DataSource dataSource;

    @Override
    public SqlConnection getConnection() throws JdbcDbException {
        try {
            return new SqlConnectionCloseable(dataSource.getConnection());
        } catch (SQLException ex) {
            throw new JdbcDbException(ex);
        }
    }
}
