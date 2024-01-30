package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

import java.time.Instant;

/**
 * Запрос в сценарии "Просмотр показаний за конкретный месяц"
 */
public interface GetMonthMeterReadingsInputRequest {
    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();

    /**
     * Первое число месяца, за который требуется выбрать показания
     *
     * @return Instant
     */
    Instant getPeriod();
}
