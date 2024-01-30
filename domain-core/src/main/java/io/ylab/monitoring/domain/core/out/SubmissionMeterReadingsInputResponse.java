package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

/**
 * Ответ в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputResponse {
    /**
     * Сохраненный объект показания счетчика
     *
     * @return Объект
     */
    MeterReading getMeterReading();
}
