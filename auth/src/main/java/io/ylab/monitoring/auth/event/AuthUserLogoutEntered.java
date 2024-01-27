package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLogoutEntered;
import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthUserLogoutEntered extends AuthMonitoringEvent implements UserLogoutEntered {
    private final String eventName = "enter in use case 'user logout'";

    private final UserLogoutInputRequest request;
}
