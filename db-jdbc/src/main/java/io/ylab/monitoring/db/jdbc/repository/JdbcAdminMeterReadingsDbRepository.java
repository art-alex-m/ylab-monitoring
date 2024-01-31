package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class JdbcAdminMeterReadingsDbRepository
        implements GetActualMeterReadingsInputDbRepository, GetMonthMeterReadingsInputDbRepository,
        ViewMeterReadingsHistoryInputDbRepository {

    private Connection connection;

    @Override
    public List<MeterReading> findActualByUser(DomainUser user) {
        return Collections.emptyList();
    }

    @Override
    public List<MeterReading> findByUserAndPeriod(DomainUser user, Instant period) {
        return Collections.emptyList();
    }

    @Override
    public List<MeterReading> findByUser(DomainUser user) {
        return Collections.emptyList();
    }
}
