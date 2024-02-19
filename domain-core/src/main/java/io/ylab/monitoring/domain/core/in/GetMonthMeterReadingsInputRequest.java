package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUserable;

import java.time.Instant;

/**
 * Запрос в сценарии "Просмотр показаний за конкретный месяц"
 */
public interface GetMonthMeterReadingsInputRequest extends DomainUserable {

    /**
     * Первое число месяца, за который требуется выбрать показания
     *
     * @return Instant
     */
    Instant getPeriod();
}
