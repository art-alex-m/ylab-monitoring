package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;

public interface MeterReadingsHistoryEntered extends MonitoringEvent {
    ViewMeterReadingsHistoryInputRequest getRequest();
}
