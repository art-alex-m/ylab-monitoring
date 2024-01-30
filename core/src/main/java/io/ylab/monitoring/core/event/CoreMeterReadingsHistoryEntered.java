package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MeterReadingsHistoryEntered;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CoreMeterReadingsHistoryEntered extends CoreMonitoringEvent implements MeterReadingsHistoryEntered {
    private static final String EVENT_NAME = "Enter in use case 'view meter readings history for user'";

    private final ViewMeterReadingsHistoryInputRequest request;

    @Builder
    public CoreMeterReadingsHistoryEntered(DomainUser user,
            Instant createdAt, ViewMeterReadingsHistoryInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
