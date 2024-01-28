package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.exception.MonitoringException;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;

/**
 * Сценарий "Подача показаний"
 */
public interface SubmissionMeterReadingsInput extends MonitoringInput {

    /**
     * Выполняет сохранение показания счетчика
     *
     * @param request Запрос на запись показания счетчика
     * @return Объект
     * @throws MonitoringException MeterReadingExistsException, MeterNotFoundException
     */
    SubmissionMeterReadingsInputResponse submit(SubmissionMeterReadingsInputRequest request) throws MonitoringException;
}
