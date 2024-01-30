package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.event.MonthMeterReadingsEntered;
import io.ylab.monitoring.domain.core.event.MonthMeterReadingsFound;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponseFactory;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.service.PeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class CoreGetMonthMeterReadingsInteractorTest {

    private final DomainUser testUser = new CoreDomainUser(UUID.randomUUID());

    private final Instant testPeriod = Instant.now();

    @Mock
    GetMonthMeterReadingsResponseFactory responseFactory;

    @Mock
    PeriodService periodService;

    @Mock
    GetMonthMeterReadingsInputDbRepository readingsDbRepository;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    GetMonthMeterReadingsResponse response;

    @Mock
    GetMonthMeterReadingsInputRequest request;

    @InjectMocks
    CoreGetMonthMeterReadingsInteractor sut;

    @BeforeEach
    void setUp() {
        given(request.getUser()).willReturn(testUser);
        given(request.getPeriod()).willReturn(testPeriod);
        given(periodService.setFistDayOfMonth(testPeriod)).willReturn(testPeriod);
        given(readingsDbRepository.findByUserAndPeriod(testUser, testPeriod)).willReturn(Collections.emptyList());
        given(responseFactory.create(Collections.emptyList())).willReturn(response);
    }

    @Test
    void givenGoodRequest_whenFind_thenSuccess() {
        ArgumentCaptor<MonthMeterReadingsEntered> enteredCaptor = ArgumentCaptor.forClass(
                MonthMeterReadingsEntered.class);
        ArgumentCaptor<MonthMeterReadingsFound> foundCaptor = ArgumentCaptor.forClass(MonthMeterReadingsFound.class);

        GetMonthMeterReadingsResponse result = sut.find(request);

        assertThat(result).isNotNull();
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(foundCaptor.capture());
        verify(readingsDbRepository, times(1)).findByUserAndPeriod(testUser, testPeriod);
        verify(responseFactory, times(1)).create(Collections.emptyList());
        MonthMeterReadingsEntered enteredEvent = enteredCaptor.getValue();
        MonthMeterReadingsFound foundEvent = foundCaptor.getValue();
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
