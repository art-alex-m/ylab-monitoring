package io.ylab.monitoring.domain.auth.event;

import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;

public interface UserLogoutEntered extends MonitoringEvent {
    UserLogoutInputRequest getRequest();
}
