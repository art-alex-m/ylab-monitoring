package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.ViewMetersEntered;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CoreViewMetersEntered extends CoreMonitoringEvent implements ViewMetersEntered {
    private final String eventName = "enter in use case 'view meters'";

    private final ViewMetersInputRequest request;
}
