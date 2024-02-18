package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

/**
 * Ответ в сценарии "Просмотр истории подачи показаний"
 */
public interface ViewMeterReadingsHistoryInputResponse {
    /**
     * Список показаний счетчиков
     *
     * @return список объектов или пустой список
     */
    List<? extends MeterReading> getMeterReadings();
}
