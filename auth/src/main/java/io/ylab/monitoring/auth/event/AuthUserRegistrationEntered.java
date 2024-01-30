package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserRegistrationEntered;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;


@Getter
public class AuthUserRegistrationEntered extends AuthMonitoringEvent implements UserRegistrationEntered {
    private static final String EVENT_NAME = "Enter in use case 'user registration'";

    private final UserRegistrationInputRequest request;

    @Builder
    public AuthUserRegistrationEntered(DomainUser user, Instant createdAt, UserRegistrationInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
