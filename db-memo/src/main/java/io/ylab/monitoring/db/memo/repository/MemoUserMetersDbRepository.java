package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoUserMetersDbRepository implements SubmissionMeterReadingsInputMeterDbRepository,
        ViewMetersInputDbRepository {

    private final Map<String, Meter> database = new HashMap<>();

    @Override
    public Optional<Meter> findByName(String meterName) {
        return Optional.ofNullable(database.get(meterName));
    }

    @Override
    public List<Meter> findAll() {
        return database.values().stream().toList();
    }

    public boolean store(Meter meter) {
        return database.putIfAbsent(meter.getName(), meter) == null;
    }
}
