package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;

public interface ViewMetersEntered extends MonitoringEvent {
    ViewMetersInputRequest getRequest();
}
