package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserRegistered;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;


@Getter
public class AuthUserRegistered extends AuthMonitoringEvent implements UserRegistered {
    private static final String EVENT_NAME = "Finish use case 'user registration'";

    @Builder
    public AuthUserRegistered(DomainUser user, Instant createdAt) {
        super(user, createdAt);
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
