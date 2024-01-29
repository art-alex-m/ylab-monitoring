package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;


/**
 * Фабрика по созданию ответов в сценарии "Получение актуальных показаний"
 */
public interface GetActualMeterReadingsInputResponseFactory {
    /**
     * Создает объект ответа для сценария "Получение актуальных показаний"
     *
     * @param meterReadings список показаний
     * @return объект
     */
    GetActualMeterReadingsInputResponse create(List<MeterReading> meterReadings);
}
