package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.Optional;

/**
 * Репозиторий получения типов показаний счетчиков в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputMeterDbRepository {
    /**
     * Тип показания счетчика, найденного по имени
     *
     * @param meterName Имя типа показания счетчика
     * @return объект
     */
    Optional<Meter> findByName(String meterName);
}
