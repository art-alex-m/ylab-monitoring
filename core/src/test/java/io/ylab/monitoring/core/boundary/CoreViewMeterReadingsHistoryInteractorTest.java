package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.event.MeterReadingsHistoryEntered;
import io.ylab.monitoring.domain.core.event.MeterReadingsHistoryFound;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
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
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CoreViewMeterReadingsHistoryInteractorTest {

    private final DomainUser testUser = new CoreDomainUser(UUID.randomUUID());

    @Mock
    ViewMeterReadingsHistoryInputResponseFactory responseFactory;

    @Mock
    ViewMeterReadingsHistoryInputDbRepository inputDbRepository;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    ViewMeterReadingsHistoryInputRequest request;

    @Mock
    ViewMeterReadingsHistoryInputResponse response;

    @InjectMocks
    CoreViewMeterReadingsHistoryInteractor sut;

    @BeforeEach
    void setUp() {
        given(request.getUser()).willReturn(testUser);
        given(responseFactory.create(Collections.emptyList())).willReturn(response);
        given(inputDbRepository.findByUser(testUser)).willReturn(Collections.emptyList());
    }

    @Test
    void givenGoodRequest_whenFind_thenSuccess() {
        ArgumentCaptor<MeterReadingsHistoryEntered> enteredCaptor = ArgumentCaptor.forClass(
                MeterReadingsHistoryEntered.class);
        ArgumentCaptor<MeterReadingsHistoryFound> foundCaptor = ArgumentCaptor.forClass(
                MeterReadingsHistoryFound.class);

        ViewMeterReadingsHistoryInputResponse result = sut.find(request);

        assertThat(result).isNotNull();
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(foundCaptor.capture());
        verify(inputDbRepository, times(1)).findByUser(testUser);
        verify(responseFactory, times(1)).create(Collections.emptyList());
        MeterReadingsHistoryEntered enteredEvent = enteredCaptor.getValue();
        MeterReadingsHistoryFound foundEvent = foundCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(foundEvent.getMeterReadings()).isNotNull().isEqualTo(Collections.emptyList());
        assertThat(foundEvent.getCreatedAt()).isNotNull();
    }
}
