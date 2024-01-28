package io.ylab.monitoring.domain.core.service;

import java.time.Instant;

/**
 * Сервис для работы с датой отнесения показаний счетчика
 */
public interface PeriodService {
    /**
     * Установить дату время в первое число
     *
     * @param period Заданный период
     * @return Скорректированный период
     */
    Instant setFistDayOfMonth(Instant period);
}
