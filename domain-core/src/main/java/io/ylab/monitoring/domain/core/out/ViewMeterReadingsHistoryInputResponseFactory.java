package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

/**
 * Фабрика по созданию ответов в сценарии "Просмотр истории подачи показаний"
 */
public interface ViewMeterReadingsHistoryInputResponseFactory {
    /**
     * Создает объект ответа для сценария ""
     *
     * @param meterReadings список показаний
     * @return объект
     */
    ViewMeterReadingsHistoryInputResponse create(List<MeterReading> meterReadings);
}
