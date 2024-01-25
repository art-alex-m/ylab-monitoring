package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

public interface ViewMeterReadingsHistoryInputResponse {
    /**
     * Список показаний счетчиков
     *
     * @return список объектов или пустой список
     */
    List<MeterReading> getMeterReadings();
}
