package io.ylab.monitoring.app.console.exception;

import io.ylab.monitoring.domain.core.exception.MonitoringException;

/**
 * Исключение для выхода из программы
 */
public class AppProgramExitException extends MonitoringException {
    public AppProgramExitException() {
        super("Application program exit exception");
    }
}
