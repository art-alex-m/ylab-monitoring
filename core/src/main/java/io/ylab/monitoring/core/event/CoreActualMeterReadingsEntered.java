package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.ActualMeterReadingsEntered;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CoreActualMeterReadingsEntered extends CoreMonitoringEvent implements ActualMeterReadingsEntered {
    private static final String EVENT_NAME = "Enter in use case 'get actual meter readings for user'";

    private final GetActualMeterReadingsInputRequest request;

    @Builder
    protected CoreActualMeterReadingsEntered(DomainUser user, Instant createdAt,
            GetActualMeterReadingsInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
