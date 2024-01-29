package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLoginEntered;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class AuthUserLoginEntered extends AuthMonitoringEvent implements UserLoginEntered {
    private static final String EVENT_NAME = "Enter in use case 'user login'";

    private final UserLoginInputRequest request;

    @Builder
    public AuthUserLoginEntered(DomainUser user, Instant createdAt, UserLoginInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
