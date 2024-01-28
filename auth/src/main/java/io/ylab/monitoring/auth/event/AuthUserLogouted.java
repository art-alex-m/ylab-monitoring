package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.auth.event.UserLogouted;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthUserLogouted extends AuthMonitoringEvent implements UserLogouted {
    private final String eventName = "finish use case 'user logout'";
}
