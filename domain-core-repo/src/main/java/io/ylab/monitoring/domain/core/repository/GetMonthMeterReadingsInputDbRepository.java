package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.time.Instant;
import java.util.List;

/**
 * Репозиторий получения показаний счетчиков в сценарии "Просмотр показаний за конкретный месяц"
 */
public interface GetMonthMeterReadingsInputDbRepository {
    List<MeterReading> findByUserAndPeriod(DomainUser user, Instant period);
}
