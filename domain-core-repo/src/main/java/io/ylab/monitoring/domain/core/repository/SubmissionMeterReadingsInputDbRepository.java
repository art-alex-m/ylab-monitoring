package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.time.Instant;

/**
 * Репозиторий хранения показаний счетчиков в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputDbRepository {

    /**
     * Сохраняет показание счетчика
     *
     * @return true если показание было сохранено
     */
    boolean save(MeterReading reading);

    /**
     * Проверяет наличие показания счетчика
     *
     * @return true если аналогичное показание уже существует
     */
    boolean existsByUserAndPeriodAndMeter(DomainUser user, Instant period, Meter meter);
}
