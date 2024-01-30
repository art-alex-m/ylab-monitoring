package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;

public interface MonthMeterReadingsEntered extends MonitoringEvent {
    GetMonthMeterReadingsInputRequest getRequest();
}
