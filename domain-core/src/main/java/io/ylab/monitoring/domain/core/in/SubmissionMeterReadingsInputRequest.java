package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUserable;
import io.ylab.monitoring.domain.core.model.Meter;

import java.time.Instant;

/**
 * Запрос в сценарии "Подача показаний"
 */
public interface SubmissionMeterReadingsInputRequest extends DomainUserable {

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
