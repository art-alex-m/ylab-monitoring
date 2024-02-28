package io.ylab.monitoring.db.jdbc.provider;

import java.sql.Connection;

/**
 * Прокси объект соединений
 */
public interface SqlConnection extends AutoCloseable {
    Connection get();
}
