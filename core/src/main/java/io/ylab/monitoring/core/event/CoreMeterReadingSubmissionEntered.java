package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MeterReadingSubmissionEntered;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CoreMeterReadingSubmissionEntered extends CoreMonitoringEvent implements MeterReadingSubmissionEntered {
    private final String eventName = "enter in use case 'submit meter reading for user'";

    private final SubmissionMeterReadingsInputRequest request;
}
