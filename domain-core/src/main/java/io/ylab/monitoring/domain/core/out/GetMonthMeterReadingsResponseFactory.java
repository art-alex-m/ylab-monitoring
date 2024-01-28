package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

/**
 * Фабрика по созданию ответов в сценарии "Просмотр показаний за конкретный месяц"
 */
public interface GetMonthMeterReadingsResponseFactory {
    GetMonthMeterReadingsResponse create(List<MeterReading> meterReadings);
}
