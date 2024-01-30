package io.ylab.monitoring.auth.boundary;

import io.ylab.monitoring.auth.event.AuthUserLogoutEntered;
import io.ylab.monitoring.auth.event.AuthUserLogouted;
import io.ylab.monitoring.domain.auth.boundary.UserLogoutInput;
import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUserLogoutInteractor implements UserLogoutInput {

    private final MonitoringEventPublisher eventPublisher;

    @Override
    public boolean logout(UserLogoutInputRequest request) {
        eventPublisher.publish(AuthUserLogoutEntered.builder()
                .user(request.getUser())
                .request(request).build());

        eventPublisher.publish(AuthUserLogouted.builder()
                .user(request.getUser()).build());

        return true;
    }
}
