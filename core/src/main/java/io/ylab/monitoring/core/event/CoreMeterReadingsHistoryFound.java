package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MeterReadingsHistoryFound;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class CoreMeterReadingsHistoryFound extends CoreMonitoringEvent implements MeterReadingsHistoryFound {
    private final String eventName = "finish use case 'view meter readings history for user'";

    private final List<MeterReading> meterReadings;
}
