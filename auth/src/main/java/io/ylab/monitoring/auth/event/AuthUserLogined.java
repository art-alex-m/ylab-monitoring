package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLogined;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthUserLogined extends AuthMonitoringEvent implements UserLogined {
    private final String eventName = "finish use case 'user login'";
}
