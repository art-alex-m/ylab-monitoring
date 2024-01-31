package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JdbcUserMetersDbRepository implements SubmissionMeterReadingsInputMeterDbRepository,
        ViewMetersInputDbRepository {

    private Connection connection;

    @Override
    public Optional<Meter> findByName(String meterName) {
        return Optional.empty();
    }

    @Override
    public List<Meter> findAll() {
        return Collections.emptyList();
    }

    public boolean store(Meter meter) {
        return false;
    }
}
