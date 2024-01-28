package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.event.CoreActualMeterReadingsEntered;
import io.ylab.monitoring.core.event.CoreActualMeterReadingsFound;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CoreGetActualMeterReadingsInteractor implements GetActualMeterReadingsInput {

    private final GetActualMeterReadingsInputResponseFactory responseFactory;

    private final GetActualMeterReadingsInputDbRepository readingsDbRepository;

    private final MonitoringEventPublisher eventPublisher;

    @Override
    public GetActualMeterReadingsInputResponse find(GetActualMeterReadingsInputRequest request) {

        eventPublisher.publish(CoreActualMeterReadingsEntered.builder()
                .user(request.getUser())
                .request(request).build());

        List<MeterReading> meterReadings = readingsDbRepository.findActualByUser(request.getUser());

        eventPublisher.publish(CoreActualMeterReadingsFound.builder()
                .meterReadings(meterReadings)
                .user(request.getUser())
                .build());

        return responseFactory.create(meterReadings);
    }
}
