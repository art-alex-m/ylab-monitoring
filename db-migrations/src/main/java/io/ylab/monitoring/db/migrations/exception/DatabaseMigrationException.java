package io.ylab.monitoring.db.migrations.exception;

/**
 * Ошибка миграций
 */
public class DatabaseMigrationException extends RuntimeException {

    public DatabaseMigrationException(Throwable ex) {
        super(ex);
    }
}
