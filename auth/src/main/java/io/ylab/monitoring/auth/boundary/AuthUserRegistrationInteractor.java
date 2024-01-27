package io.ylab.monitoring.auth.boundary;

import io.ylab.monitoring.auth.event.AuthUserRegistered;
import io.ylab.monitoring.auth.event.AuthUserRegistrationEntered;
import io.ylab.monitoring.auth.model.AuthAuthUser;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.exception.UserExistsException;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.model.DomainRole;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUserRegistrationInteractor implements UserRegistrationInput {

    private final UserRegistrationInputDbRepository inputDbRepository;

    private final MonitoringEventPublisher eventPublisher;


    @Override
    public boolean register(UserRegistrationInputRequest request) throws UserExistsException {
        eventPublisher.publish(AuthUserRegistrationEntered.builder()
                .request(request).build());

        if (inputDbRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserExistsException(request.getUsername());
        }

        AuthUser authUser = AuthAuthUser.builder()
                .username(request.getUsername())
                .password(request.getPassword()) // TODO: Password hashing
                .role(DomainRole.USER)
                .build();

        inputDbRepository.create(authUser);

        eventPublisher.publish(AuthUserRegistered.builder()
                .user(authUser).build());

        return true;
    }
}
