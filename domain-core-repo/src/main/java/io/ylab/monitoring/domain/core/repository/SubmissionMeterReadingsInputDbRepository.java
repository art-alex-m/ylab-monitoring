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
     * @param reading показание счетчика
     * @return истина если показание было сохранено
     */
    boolean save(MeterReading reading);

    /**
     * Проверяет наличие показания счетчика
     * @param user доменный пользователь
     * @param period период
     * @param meter тип показания счетчика
     * @return Истина, если показание существует
     */
    boolean existsByUserAndPeriodAndMeter(DomainUser user, Instant period, Meter meter);
}
