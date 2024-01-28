package io.ylab.monitoring.domain.auth.event;

import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.event.MonitoringEvent;

public interface UserLogined extends MonitoringEvent {
    @Override
    AuthUser getUser();
}
