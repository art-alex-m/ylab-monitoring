package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;

/**
 * Сценарий "Просмотр истории подачи показаний"
 */
public interface ViewMeterReadingsHistoryInput extends MonitoringInput {
    /**
     * Список всех показаний счетчиков
     *
     * @param request Запрос
     * @return Список всех показаний счетчиков или пустой список
     */
    ViewMeterReadingsHistoryInputResponse find(ViewMeterReadingsHistoryInputRequest request);
}
