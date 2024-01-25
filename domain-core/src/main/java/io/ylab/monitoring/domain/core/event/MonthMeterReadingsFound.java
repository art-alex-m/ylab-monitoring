package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

public interface MonthMeterReadingsFound extends MonitoringEvent {
    /**
     * Список показаний счетчиков
     *
     * @return список объектов или пустой список
     */
    List<MeterReading> getMeterReadings();
}
