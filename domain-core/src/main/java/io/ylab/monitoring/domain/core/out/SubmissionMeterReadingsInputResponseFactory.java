package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

/**
 * Фабрика по созданию ответов в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputResponseFactory {
    SubmissionMeterReadingsInputResponse create(MeterReading meterReading);
}
