package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MeterReadingSubmited;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CoreMeterReadingSubmited extends CoreMonitoringEvent implements MeterReadingSubmited {
    private final String eventName = "finish use case 'submit meter reading for user'";

    private final MeterReading meterReading;
}
