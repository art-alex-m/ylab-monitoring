package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.ActualMeterReadingsEntered;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CoreActualMeterReadingsEntered extends CoreMonitoringEvent implements ActualMeterReadingsEntered {
    private final String eventName = "enter in use case 'get actual meter readings for user'";

    private final GetActualMeterReadingsInputRequest request;
}
