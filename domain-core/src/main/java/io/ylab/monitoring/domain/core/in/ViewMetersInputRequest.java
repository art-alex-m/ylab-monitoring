package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

/**
 * Запрос на получение типов показаний счетчиков в сценарии "Просмотр перечня возможных показаний"
 */
public interface ViewMetersInputRequest {
    /**
     * Доменная информация о пользователе
     *
     * @return DomainUser
     */
    DomainUser getUser();
}
