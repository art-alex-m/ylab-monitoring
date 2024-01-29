package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLogouted;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class AuthUserLogouted extends AuthMonitoringEvent implements UserLogouted {
    private static final String EVENT_NAME = "Finish use case 'user logout'";

    @Builder
    public AuthUserLogouted(DomainUser user, Instant createdAt) {
        super(user, createdAt);
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
