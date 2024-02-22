package io.ylab.monitoring.db.jdbc.provider;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;

/**
 * Провайдер подключения к базе данных
 */
public interface SqlConnectionProvider {
    /**
     * Возвращает объект соединения для создания запросов
     *
     * @return SqlConnection
     */
    SqlConnection getConnection() throws JdbcDbException;
}
