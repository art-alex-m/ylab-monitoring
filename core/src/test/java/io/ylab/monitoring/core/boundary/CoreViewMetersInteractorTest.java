package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.event.ViewMetersEntered;
import io.ylab.monitoring.domain.core.event.ViewMetersFound;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class CoreViewMetersInteractorTest {

    private final DomainUser testUser = new CoreDomainUser(UUID.randomUUID());

    @Mock
    ViewMetersInputResponseFactory responseFactory;

    @Mock
    ViewMetersInputDbRepository metersInputDbRepository;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    ViewMetersInputRequest request;

    @Mock
    ViewMetersInputResponse response;

    @InjectMocks
    CoreViewMetersInteractor sut;

    @BeforeEach
    void setSut() {
        given(request.getUser()).willReturn(testUser);
        given(metersInputDbRepository.findAll()).willReturn(Collections.emptyList());
        given(responseFactory.create(anyList())).willReturn(response);
    }

    @Test
    void givenGoodRequest_whenFind_thenSuccess() {
        ArgumentCaptor<ViewMetersEntered> enteredCaptor = ArgumentCaptor.forClass(ViewMetersEntered.class);
        ArgumentCaptor<ViewMetersFound> foundCaptor = ArgumentCaptor.forClass(ViewMetersFound.class);

        ViewMetersInputResponse result = sut.find(request);

        assertThat(result).isNotNull();
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(foundCaptor.capture());
        verify(metersInputDbRepository, times(1)).findAll();
        verify(responseFactory, times(1)).create(Collections.emptyList());
        ViewMetersEntered enteredEvent = enteredCaptor.getValue();
        ViewMetersFound foundEvent = foundCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(foundEvent.getMeters()).isNotNull().isEqualTo(Collections.emptyList());
        assertThat(foundEvent.getCreatedAt()).isNotNull();
    }
}
