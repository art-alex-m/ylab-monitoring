package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.event.CoreMonthMeterReadingsEntered;
import io.ylab.monitoring.core.event.CoreMonthMeterReadingsFound;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponseFactory;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.service.PeriodService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Builder
public class CoreGetMonthMeterReadingsInteractor implements GetMonthMeterReadingsInput {

    private final GetMonthMeterReadingsResponseFactory responseFactory;

    private final PeriodService periodService;

    private final GetMonthMeterReadingsInputDbRepository readingsDbRepository;

    private final MonitoringEventPublisher eventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public GetMonthMeterReadingsResponse find(GetMonthMeterReadingsInputRequest request) {
        eventPublisher.publish(CoreMonthMeterReadingsEntered.builder()
                .request(request).user(request.getUser()).build());

        Instant canonicalPeriod = periodService.setFistDayOfMonth(request.getPeriod());

        List<MeterReading> meterReadings = readingsDbRepository.findByUserAndPeriod(request.getUser(), canonicalPeriod);

        eventPublisher.publish(CoreMonthMeterReadingsFound.builder()
                .meterReadings(meterReadings)
                .user(request.getUser()).build());

        return responseFactory.create(meterReadings);
    }
}
