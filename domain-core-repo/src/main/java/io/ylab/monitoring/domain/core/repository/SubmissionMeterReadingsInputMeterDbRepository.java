package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.Optional;

/**
 * Репозиторий получения типов показаний счетчиков в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputMeterDbRepository {
    Optional<Meter> findByName(String meterName);
}
