package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.util.List;

public interface ViewMeterReadingsHistoryInputDbRepository {
    List<MeterReading> findByUser(DomainUser user);
}
