package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.ViewMetersFound;
import io.ylab.monitoring.domain.core.model.Meter;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class CoreViewMetersFound extends CoreMonitoringEvent implements ViewMetersFound {
    private final String eventName = "finish use case 'view meters'";

    private final List<Meter> meters;
}
