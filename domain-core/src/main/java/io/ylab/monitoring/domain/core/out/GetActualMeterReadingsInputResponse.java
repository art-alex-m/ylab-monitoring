package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

public interface GetActualMeterReadingsInputResponse {
    /**
     * Список показаний счетчиков
     *
     * @return список объектов или пустой список
     */
    List<MeterReading> getMeterReadings();
}
