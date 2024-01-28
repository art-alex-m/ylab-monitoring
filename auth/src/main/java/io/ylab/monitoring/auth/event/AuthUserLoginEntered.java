package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLoginEntered;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthUserLoginEntered extends AuthMonitoringEvent implements UserLoginEntered {
    private final String eventName = "enter in use case 'user login'";

    private final UserLoginInputRequest request;
}
