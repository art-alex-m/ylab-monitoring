package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MeterReadingsHistoryEntered;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CoreMeterReadingsHistoryEntered extends CoreMonitoringEvent implements MeterReadingsHistoryEntered {
    private final String eventName = "enter in use case 'view meter readings history for user'";

    private final ViewMeterReadingsHistoryInputRequest request;
}
