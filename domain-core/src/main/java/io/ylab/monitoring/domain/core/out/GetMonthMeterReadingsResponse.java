package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

/**
 * Ответ в сценарии "Просмотр показаний за конкретный месяц"
 */
public interface GetMonthMeterReadingsResponse {
    /**
     * Список показаний счетчиков
     *
     * @return список объектов или пустой список
     */
    List<MeterReading> getMeterReadings();
}
