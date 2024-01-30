package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.event.CoreMeterReadingSubmissionEntered;
import io.ylab.monitoring.core.event.CoreMeterReadingSubmited;
import io.ylab.monitoring.core.model.CoreMeterReading;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.exception.MeterNotFoundException;
import io.ylab.monitoring.domain.core.exception.MeterReadingExistsException;
import io.ylab.monitoring.domain.core.exception.MonitoringException;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.service.PeriodService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Builder
public class CoreSubmissionMeterReadingsInteractor implements SubmissionMeterReadingsInput {

    private final SubmissionMeterReadingsInputResponseFactory responseFactory;

    private final MonitoringEventPublisher eventPublisher;

    private final SubmissionMeterReadingsInputMeterDbRepository meterDbRepository;

    private final SubmissionMeterReadingsInputDbRepository readingsDbRepository;

    private final PeriodService periodService;


    /**
     * {@inheritDoc}
     */
    @Override
    public SubmissionMeterReadingsInputResponse submit(SubmissionMeterReadingsInputRequest request)
            throws MonitoringException {

        eventPublisher.publish(CoreMeterReadingSubmissionEntered.builder()
                .request(request)
                .user(request.getUser()).build());

        Meter meter = meterDbRepository.findByName(request.getMeterName())
                .orElseThrow(() -> new MeterNotFoundException(request.getMeterName()));

        Instant canonicalPeriod = periodService.setFistDayOfMonth(request.getPeriod());

        MeterReading meterReading = CoreMeterReading.builder()
                .meter(meter)
                .user(request.getUser())
                .value(request.getValue())
                .period(canonicalPeriod)
                .build();

        if (readingsDbRepository.existsByUserAndPeriodAndMeter(meterReading.getUser(),
                meterReading.getPeriod(), meterReading.getMeter())) {
            throw new MeterReadingExistsException(request.getMeterName());
        }

        readingsDbRepository.save(meterReading);

        eventPublisher.publish(CoreMeterReadingSubmited.builder()
                .meterReading(meterReading)
                .user(request.getUser()).build());

        return responseFactory.create(meterReading);
    }
}
