package io.ylab.monitoring.domain.core.exception;

/**
 * Базовый класс исключения бизнес логики работы с показаниями счетчиков
 */
public abstract class DomainCoreMonitoringException extends BusinessLogicMonitoringException {
    public DomainCoreMonitoringException(String message) {
        super(message);
    }

    public DomainCoreMonitoringException(Throwable throwable) {
        super(throwable);
    }
}
