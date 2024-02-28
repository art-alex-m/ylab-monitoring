package io.ylab.monitoring.app.springboot.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Schema(name = "MeterReading")
public class AppMeterReading implements MeterReading {

    private final DomainUser user;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Instant period;

    @Schema(implementation = AppMeter.class)
    private final Meter meter;

    private final long value;

    private final UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Instant createdAt;
}
