package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MeterReadingSubmissionEntered;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CoreMeterReadingSubmissionEntered extends CoreMonitoringEvent implements MeterReadingSubmissionEntered {
    private static final String EVENT_NAME = "Enter in use case 'submit meter reading for user'";

    private final SubmissionMeterReadingsInputRequest request;

    @Builder
    public CoreMeterReadingSubmissionEntered(DomainUser user,
            Instant createdAt, SubmissionMeterReadingsInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
