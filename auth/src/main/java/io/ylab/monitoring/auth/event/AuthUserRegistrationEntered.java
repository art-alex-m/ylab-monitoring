package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserRegistrationEntered;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthUserRegistrationEntered extends AuthMonitoringEvent implements UserRegistrationEntered {
    private final String eventName = "enter in use case 'user registration'";

    private final UserRegistrationInputRequest request;
}
