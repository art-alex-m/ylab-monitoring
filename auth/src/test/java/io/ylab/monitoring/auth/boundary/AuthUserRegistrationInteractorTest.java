package io.ylab.monitoring.auth.boundary;

import io.ylab.monitoring.domain.auth.event.UserRegistered;
import io.ylab.monitoring.domain.auth.event.UserRegistrationEntered;
import io.ylab.monitoring.domain.auth.exception.UserExistsException;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.model.DomainRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoInteractions;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AuthUserRegistrationInteractorTest {

    private final String testUsername = "test-user";

    private final String testPassword = "test-password";

    @Mock
    UserRegistrationInputDbRepository inputDbRepository;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRegistrationInputRequest request;

    @InjectMocks
    AuthUserRegistrationInteractor sut;

    @BeforeEach
    void setUp() {
        given(request.getUsername()).willReturn(testUsername);
    }

    @Test
    void givenRequest_whenRegister_thenSuccess() {
        given(request.getPassword()).willReturn(testPassword);
        given(passwordEncoder.encode(testPassword)).willReturn(testPassword);
        given(inputDbRepository.existsByUsername(testUsername)).willReturn(false);
        ArgumentCaptor<UserRegistrationEntered> enteredCaptor = ArgumentCaptor.forClass(UserRegistrationEntered.class);
        ArgumentCaptor<UserRegistered> registeredCaptor = ArgumentCaptor.forClass(UserRegistered.class);
        ArgumentCaptor<AuthUser> authUserCaptor = ArgumentCaptor.forClass(AuthUser.class);

        boolean result = sut.register(request);

        assertThat(result).isTrue();
        verify(passwordEncoder, times(1)).encode(testPassword);
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(registeredCaptor.capture());
        verify(inputDbRepository, times(1)).create(authUserCaptor.capture());
        verify(inputDbRepository, times(1)).existsByUsername(testUsername);
        verifyNoMoreInteractions(inputDbRepository);
        AuthUser capturedUser = authUserCaptor.getValue();
        assertThat(capturedUser).isNotNull();
        assertThat(capturedUser.getPassword()).isEqualTo(testPassword);
        assertThat(capturedUser.getUsername()).isEqualTo(testUsername);
        assertThat(capturedUser.getRole()).isEqualTo(DomainRole.USER);
        assertThat(capturedUser.getId()).isInstanceOf(UUID.class);
        UserRegistrationEntered enteredEvent = enteredCaptor.getValue();
        UserRegistered registeredEvent = registeredCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNull();
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        assertThat(registeredEvent).isNotNull();
        assertThat(registeredEvent.getUser()).isNotNull().isEqualTo(capturedUser);
        assertThat(registeredEvent.getCreatedAt()).isNotNull();
    }

    @Test
    void givenDbUsernameExists_whenRegister_thenUserExistsException() {
        AuthUser authUser = mock(AuthUser.class);
        given(inputDbRepository.existsByUsername(testUsername)).willReturn(true);
        ArgumentCaptor<UserRegistrationEntered> enteredCaptor = ArgumentCaptor.forClass(UserRegistrationEntered.class);

        Throwable result = catchThrowable(() -> sut.register(request));

        assertThat(result).isInstanceOf(UserExistsException.class).hasMessageContaining(testUsername);
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(inputDbRepository, times(1)).existsByUsername(testUsername);
        verifyNoMoreInteractions(inputDbRepository);
        verifyNoMoreInteractions(eventPublisher);
        verifyNoInteractions(passwordEncoder);
        UserRegistrationEntered enteredEvent = enteredCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNull();
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
    }
}
