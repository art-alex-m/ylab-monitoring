package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;

/**
 * Сценарий "Просмотр показаний за конкретный месяц"
 */
public interface GetMonthMeterReadingsInput {
    /**
     * Возвращает список показаний по заданному месяцу и году
     *
     * @param request Запрос
     * @return Список показаний или пустой список
     */
    GetMonthMeterReadingsResponse find(GetMonthMeterReadingsInputRequest request);
}
