package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MeterReadingSubmited;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CoreMeterReadingSubmited extends CoreMonitoringEvent implements MeterReadingSubmited {
    private static final String EVENT_NAME = "Finish use case 'submit meter reading for user'";

    private final MeterReading meterReading;

    @Builder
    public CoreMeterReadingSubmited(DomainUser user, Instant createdAt, MeterReading meterReading) {
        super(user, createdAt);
        this.meterReading = meterReading;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
