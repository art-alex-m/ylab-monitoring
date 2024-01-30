package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

/**
 * Репозиторий получения показаний счетчиков в сценарии "Просмотр истории подачи показаний"
 */
public interface ViewMeterReadingsHistoryInputDbRepository {
    /**
     * Список показаний счетчиков для пользователя
     *
     * @param user DomainUser
     * @return список показаний или пустой список
     */
    List<MeterReading> findByUser(DomainUser user);
}
