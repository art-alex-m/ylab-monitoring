package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

/**
 * Репозиторий получения показаний счетчиков в сценарии "Получение актуальных показаний"
 */
public interface GetActualMeterReadingsInputDbRepository {
    List<MeterReading> findActualByUser(DomainUser user);
}
