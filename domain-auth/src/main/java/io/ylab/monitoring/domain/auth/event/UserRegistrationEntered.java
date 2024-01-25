package io.ylab.monitoring.domain.auth.event;

import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;

public interface UserRegistrationEntered extends MonitoringEvent {
    UserRegistrationInputRequest getRequest();
}
