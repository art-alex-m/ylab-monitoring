package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLogined;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;


@Getter
public class AuthUserLogined extends AuthMonitoringEvent implements UserLogined {
    private static final String EVENT_NAME = "Finish use case 'user login'";

    @Builder
    public AuthUserLogined(AuthUser user, Instant createdAt) {
        super(user, createdAt);
    }

    @Override
    public AuthUser getUser() {
        return (AuthUser) super.getUser();
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
