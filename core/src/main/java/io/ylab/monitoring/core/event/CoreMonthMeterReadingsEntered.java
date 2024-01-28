package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MonthMeterReadingsEntered;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CoreMonthMeterReadingsEntered extends CoreMonitoringEvent implements MonthMeterReadingsEntered {
    private final String eventName = "enter in use case 'get month meter readings for user'";

    private final GetMonthMeterReadingsInputRequest request;
}
