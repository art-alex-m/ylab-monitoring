package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Запрос в сценарии "Просмотр истории подачи показаний"
 */
public interface ViewMeterReadingsHistoryInputRequest {
    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();
}
