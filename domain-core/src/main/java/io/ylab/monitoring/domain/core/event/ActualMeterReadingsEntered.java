package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;

public interface ActualMeterReadingsEntered extends MonitoringEvent {
    GetActualMeterReadingsInputRequest getRequest();
}
