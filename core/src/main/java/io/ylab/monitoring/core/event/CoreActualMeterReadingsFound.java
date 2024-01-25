package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.ActualMeterReadingsFound;
import io.ylab.monitoring.domain.core.model.Meter;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class CoreActualMeterReadingsFound extends CoreMonitoringEvent implements ActualMeterReadingsFound {
    private final String eventName = "finish use case 'get actual meter readings for user'";

    private final List<Meter> meterReadings;
}
