package io.ylab.monitoring.auth.boundary;

import io.ylab.monitoring.auth.event.AuthUserLogoutEntered;
import io.ylab.monitoring.auth.event.AuthUserLogouted;
import io.ylab.monitoring.domain.auth.in.UserLogoutInputRequest;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.model.DomainUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthUserLogoutInteractorTest {

    @Mock
    DomainUser domainUser;

    @Mock
    UserLogoutInputRequest request;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @InjectMocks
    AuthUserLogoutInteractor sut;

    @Test
    void givenRequest_whenLogout_theSuccess() {
        given(request.getUser()).willReturn(domainUser);
        ArgumentCaptor<AuthUserLogoutEntered> enteredCaptor = ArgumentCaptor.forClass(AuthUserLogoutEntered.class);
        ArgumentCaptor<AuthUserLogouted> foundCaptor = ArgumentCaptor.forClass(AuthUserLogouted.class);

        boolean result = sut.logout(request);

        assertThat(result).isTrue();
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(foundCaptor.capture());
        AuthUserLogoutEntered enteredEvent = enteredCaptor.getValue();
        AuthUserLogouted foundEvent = foundCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNotNull().isEqualTo(domainUser);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getUser()).isNotNull().isEqualTo(domainUser);
        assertThat(foundEvent.getCreatedAt()).isNotNull();
    }
}