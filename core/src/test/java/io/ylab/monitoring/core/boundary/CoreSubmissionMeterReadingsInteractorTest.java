package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.domain.core.event.MeterReadingSubmissionEntered;
import io.ylab.monitoring.domain.core.event.MeterReadingSubmited;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.exception.MeterNotFoundException;
import io.ylab.monitoring.domain.core.exception.MeterReadingExistsException;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.service.PeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoInteractions;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CoreSubmissionMeterReadingsInteractorTest {

    private final DomainUser testUser = new CoreDomainUser(UUID.randomUUID());

    private final Instant testPeriod = Instant.now();

    private final Meter testMeter = new CoreMeter(UUID.randomUUID(), "test");

    private final long testValue = 10;

    @Mock
    SubmissionMeterReadingsInputResponseFactory responseFactory;

    @Mock
    MonitoringEventPublisher eventPublisher;

    @Mock
    SubmissionMeterReadingsInputMeterDbRepository meterDbRepository;

    @Mock
    SubmissionMeterReadingsInputDbRepository readingsDbRepository;

    @Mock
    PeriodService periodService;

    @Mock
    SubmissionMeterReadingsInputRequest request;

    @Mock
    SubmissionMeterReadingsInputResponse response;

    @InjectMocks
    CoreSubmissionMeterReadingsInteractor sut;

    @BeforeEach
    void setUp() {
        given(request.getUser()).willReturn(testUser);
        given(request.getMeterName()).willReturn(testMeter.getName());
    }

    @Test
    void givenGoodRequest_whenSubmit_thenSuccess() {
        given(request.getPeriod()).willReturn(testPeriod);
        given(request.getValue()).willReturn(testValue);
        given(periodService.setFistDayOfMonth(testPeriod)).willReturn(testPeriod);
        given(responseFactory.create(any(MeterReading.class))).willReturn(response);
        given(meterDbRepository.findByName(testMeter.getName())).willReturn(Optional.of(testMeter));
        given(readingsDbRepository.existsByUserAndPeriodAndMeter(testUser, testPeriod, testMeter)).willReturn(false);
        ArgumentCaptor<MeterReadingSubmissionEntered> enteredCaptor = ArgumentCaptor.forClass(
                MeterReadingSubmissionEntered.class);
        ArgumentCaptor<MeterReadingSubmited> foundCaptor = ArgumentCaptor.forClass(MeterReadingSubmited.class);
        ArgumentCaptor<MeterReading> readingCaptor = ArgumentCaptor.forClass(MeterReading.class);

        SubmissionMeterReadingsInputResponse result = sut.submit(request);

        assertThat(result).isNotNull();
        verify(meterDbRepository, times(1)).findByName(testMeter.getName());
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        verify(eventPublisher, times(1)).publish(foundCaptor.capture());
        verify(readingsDbRepository, times(1)).save(readingCaptor.capture());
        MeterReading meterReading = readingCaptor.getValue();
        assertThat(meterReading).isNotNull();
        assertThat(meterReading.getMeter()).isNotNull().isEqualTo(testMeter);
        assertThat(meterReading.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(meterReading.getValue()).isEqualTo(testValue);
        assertThat(meterReading.getPeriod()).isNotNull().isEqualTo(testPeriod);
        assertThat(meterReading.getCreatedAt()).isNotNull();
        verify(responseFactory, times(1)).create(meterReading);
        MeterReadingSubmissionEntered enteredEvent = enteredCaptor.getValue();
        MeterReadingSubmited foundEvent = foundCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(foundEvent.getMeterReading()).isNotNull().isEqualTo(meterReading);
        assertThat(foundEvent.getCreatedAt()).isNotNull();
    }

    @Test
    void givenDbNoMeter_whenSubmit_thenThrowMeterNotFoundException() {
        given(meterDbRepository.findByName(request.getMeterName())).willReturn(Optional.empty());
        ArgumentCaptor<MeterReadingSubmissionEntered> enteredCaptor = ArgumentCaptor.forClass(
                MeterReadingSubmissionEntered.class);

        Throwable result = catchThrowable(() -> sut.submit(request));

        assertThat(result).isInstanceOf(MeterNotFoundException.class).hasMessageContaining(testMeter.getName());
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        MeterReadingSubmissionEntered enteredEvent = enteredCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        verifyNoMoreInteractions(eventPublisher);
        verifyNoInteractions(periodService);
        verifyNoInteractions(readingsDbRepository);
        verifyNoInteractions(responseFactory);
    }

    @Test
    void givenDbMeterReadingExists_whenSubmit_thenThrowMeterReadingExistsException() {
        given(request.getPeriod()).willReturn(testPeriod);
        given(request.getValue()).willReturn(testValue);
        given(meterDbRepository.findByName(request.getMeterName())).willReturn(Optional.of(testMeter));
        given(periodService.setFistDayOfMonth(testPeriod)).willReturn(testPeriod);
        given(readingsDbRepository.existsByUserAndPeriodAndMeter(testUser, testPeriod, testMeter)).willReturn(true);
        ArgumentCaptor<MeterReadingSubmissionEntered> enteredCaptor = ArgumentCaptor.forClass(
                MeterReadingSubmissionEntered.class);

        Throwable result = catchThrowable(() -> sut.submit(request));

        assertThat(result).isInstanceOf(MeterReadingExistsException.class).hasMessageContaining(testMeter.getName());
        verify(periodService, times(1)).setFistDayOfMonth(testPeriod);
        verify(readingsDbRepository, times(1))
                .existsByUserAndPeriodAndMeter(testUser, testPeriod, testMeter);
        verify(eventPublisher, times(1)).publish(enteredCaptor.capture());
        MeterReadingSubmissionEntered enteredEvent = enteredCaptor.getValue();
        assertThat(enteredEvent).isNotNull();
        assertThat(enteredEvent.getRequest()).isNotNull().isEqualTo(request);
        assertThat(enteredEvent.getUser()).isNotNull().isEqualTo(testUser);
        assertThat(enteredEvent.getCreatedAt()).isNotNull();
        verifyNoMoreInteractions(eventPublisher);
        verifyNoMoreInteractions(readingsDbRepository);
        verifyNoInteractions(responseFactory);
    }
}
