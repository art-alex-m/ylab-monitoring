package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLogoutEntered;
import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class AuthUserLogoutEntered extends AuthMonitoringEvent implements UserLogoutEntered {
    private static final String EVENT_NAME = "Enter in use case 'user logout'";

    private final UserLogoutInputRequest request;

    @Builder
    public AuthUserLogoutEntered(DomainUser user, Instant createdAt, UserLogoutInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
