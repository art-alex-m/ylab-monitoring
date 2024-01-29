package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MonthMeterReadingsEntered;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CoreMonthMeterReadingsEntered extends CoreMonitoringEvent implements MonthMeterReadingsEntered {
    private static final String EVENT_NAME = "Enter in use case 'get month meter readings for user'";

    private final GetMonthMeterReadingsInputRequest request;

    @Builder
    public CoreMonthMeterReadingsEntered(DomainUser user,
            Instant createdAt, GetMonthMeterReadingsInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
