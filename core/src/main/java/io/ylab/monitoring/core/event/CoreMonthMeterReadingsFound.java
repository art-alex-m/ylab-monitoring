package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MonthMeterReadingsFound;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class CoreMonthMeterReadingsFound extends CoreMonitoringEvent implements MonthMeterReadingsFound {
    private final String eventName = "finish use case 'get month meter readings for user'";

    private final List<MeterReading> meterReadings;
}
