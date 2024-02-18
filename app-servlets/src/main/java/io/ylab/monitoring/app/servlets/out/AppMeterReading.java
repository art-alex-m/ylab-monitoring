package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppMeterReading implements MeterReading {

    private final DomainUser user;

    private final Instant period;

    private final Meter meter;

    private final long value;

    private final UUID id;

    private final Instant createdAt;
}
