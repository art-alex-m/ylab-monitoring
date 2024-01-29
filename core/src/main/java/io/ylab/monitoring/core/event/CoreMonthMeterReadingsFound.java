package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MonthMeterReadingsFound;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class CoreMonthMeterReadingsFound extends CoreMonitoringEvent implements MonthMeterReadingsFound {
    private static final String EVENT_NAME = "Finish use case 'get month meter readings for user'";

    private final List<MeterReading> meterReadings;

    @Builder
    public CoreMonthMeterReadingsFound(DomainUser user,
            Instant createdAt, List<MeterReading> meterReadings) {
        super(user, createdAt);
        this.meterReadings = meterReadings;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
