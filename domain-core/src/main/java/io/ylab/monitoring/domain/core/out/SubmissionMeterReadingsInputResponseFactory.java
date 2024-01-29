package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

/**
 * Фабрика по созданию ответов в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputResponseFactory {
    /**
     * Создает объект ответа для сценария "Подача показаний"
     *
     * @param meterReading показание счетчика
     * @return объект
     */
    SubmissionMeterReadingsInputResponse create(MeterReading meterReading);
}
