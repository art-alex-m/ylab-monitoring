package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

/**
 * Репозиторий получения показаний счетчиков в сценарии "Просмотр истории подачи показаний"
 */
public interface ViewMeterReadingsHistoryInputDbRepository {
    List<MeterReading> findByUser(DomainUser user);
}
