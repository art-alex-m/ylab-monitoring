package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.Optional;


public interface SubmissionMeterReadingsInputMeterDbRepository {

    Optional<Meter> findByName(String meterName);
}
