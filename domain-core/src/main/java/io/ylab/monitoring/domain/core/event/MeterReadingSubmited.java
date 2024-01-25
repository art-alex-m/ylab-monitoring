package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.model.MeterReading;

public interface MeterReadingSubmited extends MonitoringEvent {
    /**
     * Возвращает сохраненный объект показания счетчика
     *
     * @return объект
     */
    MeterReading getMeterReading();
}
