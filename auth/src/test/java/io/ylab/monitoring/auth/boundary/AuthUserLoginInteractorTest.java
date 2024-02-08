package io.ylab.monitoring.auth.boundary;

import io.ylab.monitoring.domain.auth.event.UserLoginEntered;
import io.ylab.monitoring.domain.auth.event.UserLogined;
import io.ylab.monitoring.domain.auth.exception.UserNotFoundException;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoInteractions;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AuthUserLoginInteractorTest {

    private final String testUsername = "testUsername";
    private final String testPassword = "test-password";
    private final String testHash = "test-hash";

    @Mock
    AuthUser authUser;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    UserLoginInputDbRepository inputDbRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserLoginInputRequest request;

    @InjectMocks
    AuthUserLoginInteractor sut;

    @BeforeEach
    void setUp() {
        given(request.getUsername()).willReturn(testUsername);
    }

    @Test
    void givenGoodRequest_whenLogin_thenSuccess() {
        given(request.getPassword()).willReturn(testPassword);
        given(authUser.getPassword()).willReturn(testHash);
        given(authUser.getRole()).willReturn(DomainRole.USER);
        given(authUser.getId()).willReturn(UUID.randomUUID());
        given(inputDbRepository.findByUsername(testUsername)).willReturn(Optional.of(authUser));
        given(passwordEncoder.matches(testPassword, authUser.getPassword())).willReturn(true);
        ArgumentCaptor<UserLoginEntered> enteredCaptor = ArgumentCaptor.forClass(UserLoginEntered.class);
        ArgumentCaptor<UserLogined> loginedCaptor = ArgumentCaptor.forClass(UserLogined.class);

        UserLoginInputResponse result = sut.login(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(authUser.getId());
        assertThat(result.getRole()).isEqualTo(authUser.getRole());
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(loginedCaptor.capture());
        verify(passwordEncoder, times(1)).matches(testPassword, authUser.getPassword());
        verify(inputDbRepository, times(1)).findByUsername(testUsername);
        verifyNoMoreInteractions(passwordEncoder);
        UserLoginEntered enteredEvent = enteredCaptor.getValue();
        UserLogined loginedEvent = loginedCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        assertThat(loginedEvent).isNotNull();
        assertThat(loginedEvent.getUser()).isNotNull().isEqualTo(authUser);
        assertThat(loginedEvent.getCreatedAt()).isNotNull();
    }

    @Test
    void givenDbNoUser_whenLogin_thenUserNotFoundException() {
        given(inputDbRepository.findByUsername(testUsername)).willReturn(Optional.empty());
        ArgumentCaptor<UserLoginEntered> enteredCaptor = ArgumentCaptor.forClass(UserLoginEntered.class);

        Throwable result = catchThrowable(() -> sut.login(request));

        assertThat(result).isInstanceOf(UserNotFoundException.class).hasMessageContaining(testUsername);
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(inputDbRepository, times(1)).findByUsername(testUsername);
        verifyNoMoreInteractions(eventPublisher);
        verifyNoInteractions(passwordEncoder);
        verifyNoMoreInteractions(inputDbRepository);
        UserLoginEntered enteredEvent = enteredCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
    }

    @Test
    void givenPasswordNotMatch_whenLogin_thenUserNotFoundException() {
        given(request.getPassword()).willReturn(testPassword);
        given(authUser.getPassword()).willReturn(testHash);
        given(inputDbRepository.findByUsername(testUsername)).willReturn(Optional.of(authUser));
        given(passwordEncoder.matches(testPassword, authUser.getPassword())).willReturn(false);
        ArgumentCaptor<UserLoginEntered> enteredCaptor = ArgumentCaptor.forClass(UserLoginEntered.class);

        Throwable result = catchThrowable(() -> sut.login(request));

        assertThat(result).isInstanceOf(UserNotFoundException.class).hasMessageContaining(testUsername);
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(inputDbRepository, times(1)).findByUsername(testUsername);
        verify(passwordEncoder, times(1)).matches(testPassword, authUser.getPassword());
        verifyNoMoreInteractions(eventPublisher);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(inputDbRepository);
        UserLoginEntered enteredEvent = enteredCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
    }
}
