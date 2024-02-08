package io.ylab.monitoring.db.jdbc.exeption;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Исключения модуля Jdbc
 */
public class JdbcDbException extends MonitoringException {
    public JdbcDbException(String message) {
        super(message);
    }

    public JdbcDbException(Throwable throwable) {
        super(throwable);
    }
}
