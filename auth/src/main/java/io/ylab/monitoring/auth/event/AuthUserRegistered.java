package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserRegistered;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthUserRegistered extends AuthMonitoringEvent implements UserRegistered {
    private final String eventName = "finish use case 'user registration'";
}
