package io.ylab.monitoring.db.jdbc.model;

import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class JdbcMeterReading implements MeterReading {
    public static final String USER = "user_uuid";
    public static final String PERIOD = "period";
    public static final String METER_ID = "meter_uuid";
    public static final String METER_NAME = "meter_name";
    public static final String VALUE = "value";
    public static final String ID = "uuid";
    public static final String CREATED_AT = "created_at";


    @NonNull
    private final DomainUser user;

    @NonNull
    private final Instant period;

    @NonNull
    private final Meter meter;

    private final long value;

    @NonNull
    private final UUID id;

    @NonNull
    private final Instant createdAt;
}
