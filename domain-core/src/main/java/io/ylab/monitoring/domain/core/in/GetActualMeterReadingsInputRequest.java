package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Запрос в сценарии "Получение актуальных показаний"
 */
public interface GetActualMeterReadingsInputRequest {
    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();
}
