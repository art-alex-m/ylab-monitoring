package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;

import java.time.Instant;

/**
 * Запрос в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputRequest {
    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();

    /**
     * Имя типа показания счетчика.
     *
     * @return зарегистрированная в базе строка
     * @see Meter#getName()
     */
    String getMeterName();

    /**
     * Значение показания счетчика
     *
     * @return целое
     */
    long getValue();

    /**
     * Месяц, за который подается показание
     *
     * @return Дата время
     */
    Instant getPeriod();
}
