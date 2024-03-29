package io.ylab.monitoring.domain.auth.event;

import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;

public interface UserLoginEntered extends MonitoringEvent {
    UserLoginInputRequest getRequest();
}
