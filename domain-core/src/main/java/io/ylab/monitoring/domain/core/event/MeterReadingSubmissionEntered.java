package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;

public interface MeterReadingSubmissionEntered extends MonitoringEvent {

    SubmissionMeterReadingsInputRequest getRequest();
}
