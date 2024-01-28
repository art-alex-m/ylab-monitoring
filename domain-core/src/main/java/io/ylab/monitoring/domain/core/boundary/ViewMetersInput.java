package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;

/**
 * Сценарий "Просмотр перечня возможных показаний"
 */
public interface ViewMetersInput extends MonitoringInput {
    /**
     * Список типов показаний счетчиков
     *
     * @param request список
     * @return список типов показаний или пустой список
     */
    ViewMetersInputResponse find(ViewMetersInputRequest request);
}
