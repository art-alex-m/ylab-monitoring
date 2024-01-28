package io.ylab.monitoring.auth.boundary;

import io.ylab.monitoring.auth.event.AuthUserLoginEntered;
import io.ylab.monitoring.auth.event.AuthUserLogined;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.exception.UserNotFoundException;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUserLoginInteractor implements UserLoginInput {

    private final MonitoringEventPublisher eventPublisher;

    private final UserLoginInputDbRepository inputDbRepository;

    @Override
    public boolean login(UserLoginInputRequest request) throws UserNotFoundException {
        eventPublisher.publish(AuthUserLoginEntered.builder()
                .request(request).build());

        AuthUser user = inputDbRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(request.getUsername()));

        if (request.getPassword().isEmpty() || !user.getPassword().equals(request.getPassword())) {
            throw new UserNotFoundException(request.getUsername());
        }

        eventPublisher.publish(AuthUserLogined.builder().user(user).build());

        return true;
    }
}
