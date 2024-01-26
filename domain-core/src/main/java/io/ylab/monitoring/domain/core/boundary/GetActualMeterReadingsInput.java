package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;

/**
 * Сценарий "Получение актуальных показаний"
 */
public interface GetActualMeterReadingsInput extends MonitoringInput {

    /**
     * Список найденных актуальных показаний счетчиков
     *
     * @param request Запрос
     * @return список найденных актуальных показаний или пустой список
     */
    GetActualMeterReadingsInputResponse find(GetActualMeterReadingsInputRequest request);
}
