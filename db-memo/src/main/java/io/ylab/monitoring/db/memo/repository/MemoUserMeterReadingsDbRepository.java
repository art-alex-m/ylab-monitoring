package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MemoUserMeterReadingsDbRepository
        implements GetActualMeterReadingsInputDbRepository, GetMonthMeterReadingsInputDbRepository,
        SubmissionMeterReadingsInputDbRepository, ViewMeterReadingsHistoryInputDbRepository {

    private final MemoMeterReadingsDatabase database;

    @Override
    public List<MeterReading> findActualByUser(DomainUser user) {
        return Optional.ofNullable(database.read(user.getId()))
                .map(map -> map.firstEntry().getValue())
                .map(map -> map.values().stream())
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<MeterReading> findByUserAndPeriod(DomainUser user, Instant period) {
        return Optional.ofNullable(database.read(user.getId()))
                .map(map -> map.get(period))
                .map(map -> map.values().stream())
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }

    @Override
    public boolean save(MeterReading reading) {
        return database.store(reading) == null;
    }

    @Override
    public boolean existsByUserAndPeriodAndMeter(DomainUser user, Instant period, Meter meter) {
        return Optional.ofNullable(database.read(user.getId()))
                .map(map -> map.get(period))
                .map(map -> map.get(meter.getName()))
                .isPresent();
    }

    @Override
    public List<MeterReading> findByUser(DomainUser user) {
        return Optional.ofNullable(database.read(user.getId()))
                .map(map -> map.values().stream())
                .map(stream -> stream.flatMap(map -> map.values().stream()))
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }
}
