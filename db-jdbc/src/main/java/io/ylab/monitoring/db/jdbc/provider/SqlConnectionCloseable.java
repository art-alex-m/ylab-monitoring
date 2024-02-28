package io.ylab.monitoring.db.jdbc.provider;

import lombok.AllArgsConstructor;

import java.sql.Connection;

@AllArgsConstructor
public class SqlConnectionCloseable implements SqlConnection {

    private final Connection connection;

    @Override
    public Connection get() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
