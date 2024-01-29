package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MemoAdminMeterReadingsDbRepository
        implements GetActualMeterReadingsInputDbRepository, GetMonthMeterReadingsInputDbRepository,
        ViewMeterReadingsHistoryInputDbRepository {

    private final MemoMeterReadingsDatabase database;

    @Override
    public List<MeterReading> findActualByUser(DomainUser user) {
        return Optional.ofNullable(database.getDb())
                .map(map -> map.values().stream())
                .map(stream -> stream.map(navMap -> navMap.firstEntry().getValue()))
                .map(stream -> stream.flatMap(map -> map.values().stream()))
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<MeterReading> findByUserAndPeriod(DomainUser user, Instant period) {
        return Optional.ofNullable(database.getDb())
                .map(map -> map.values().stream())
                .map(stream -> stream
                        .filter(navMap -> navMap.containsKey(period))
                        .map(navMap -> navMap.get(period))
                        .flatMap(navMap -> navMap.values().stream())
                )
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<MeterReading> findByUser(DomainUser user) {
        return Optional.ofNullable(database.getDb())
                .map(map -> map.values().stream())
                .map(stream -> stream.flatMap(map -> map.values().stream()))
                .map(stream -> stream.flatMap(map -> map.values().stream()))
                .map(Stream::toList)
                .orElse(Collections.emptyList());
    }
}
