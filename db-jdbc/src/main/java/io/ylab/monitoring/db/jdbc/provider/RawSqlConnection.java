package io.ylab.monitoring.db.jdbc.provider;

import lombok.AllArgsConstructor;

import java.sql.Connection;

/**
 * Прокси объект соединения для сохранения обратной совместимости
 */
@AllArgsConstructor
public class RawSqlConnection implements SqlConnection {

    private final Connection connection;

    @Override
    public Connection get() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        /// do nothing
    }
}
