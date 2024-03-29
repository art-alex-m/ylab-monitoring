package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.event.ActualMeterReadingsEntered;
import io.ylab.monitoring.domain.core.event.ActualMeterReadingsFound;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class CoreGetActualMeterReadingsInteractorTest {

    private final DomainUser testUser = new CoreDomainUser(UUID.randomUUID());

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    GetActualMeterReadingsInputResponseFactory responseFactory;

    @Mock
    GetActualMeterReadingsInputDbRepository readingsDbRepository;

    @Mock
    GetActualMeterReadingsInputRequest request;

    @Mock
    GetActualMeterReadingsInputResponse response;

    @InjectMocks
    CoreGetActualMeterReadingsInteractor sut;

    @BeforeEach
    void setUp() {
        given(responseFactory.create(Collections.emptyList())).willReturn(response);
        given(readingsDbRepository.findActualByUser(testUser)).willReturn(Collections.emptyList());
        given(request.getUser()).willReturn(testUser);
    }

    @Test
    void givenGoodRequest_whenFind_thenSuccess() {
        ArgumentCaptor<ActualMeterReadingsEntered> enteredCaptor = ArgumentCaptor.forClass(
                ActualMeterReadingsEntered.class);
        ArgumentCaptor<ActualMeterReadingsFound> foundCaptor = ArgumentCaptor.forClass(ActualMeterReadingsFound.class);

        GetActualMeterReadingsInputResponse result = sut.find(request);

        assertThat(result).isNotNull();
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(foundCaptor.capture());
        verify(readingsDbRepository, times(1)).findActualByUser(testUser);
        verify(responseFactory, times(1)).create(Collections.emptyList());
        ActualMeterReadingsEntered enteredEvent = enteredCaptor.getValue();
        ActualMeterReadingsFound foundEvent = foundCaptor.getValue();
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