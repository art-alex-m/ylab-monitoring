package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.time.Instant;
import java.util.List;

/**
 * Репозиторий получения показаний счетчиков в сценарии "Просмотр показаний за конкретный месяц"
 */
public interface GetMonthMeterReadingsInputDbRepository {
    /**
     * Список показаний счетчика по пользователю и периоду
     *
     * @param user   Доменный пользователь
     * @param period Период
     * @return список показаний или пустой список
     */
    List<MeterReading> findByUserAndPeriod(DomainUser user, Instant period);
}
