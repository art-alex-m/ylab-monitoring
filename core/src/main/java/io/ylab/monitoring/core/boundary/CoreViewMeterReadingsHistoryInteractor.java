package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.event.CoreMeterReadingsHistoryEntered;
import io.ylab.monitoring.core.event.CoreMeterReadingsHistoryFound;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
public class CoreViewMeterReadingsHistoryInteractor implements ViewMeterReadingsHistoryInput {

    private final ViewMeterReadingsHistoryInputResponseFactory responseFactory;

    private final ViewMeterReadingsHistoryInputDbRepository readingsDbRepository;

    private final MonitoringEventPublisher eventPublisher;

    @Override
    public ViewMeterReadingsHistoryInputResponse find(ViewMeterReadingsHistoryInputRequest request) {

        eventPublisher.publish(CoreMeterReadingsHistoryEntered.builder()
                .user(request.getUser())
                .request(request).build());

        List<MeterReading> meterReadings = readingsDbRepository.findByUser(request.getUser());

        eventPublisher.publish(CoreMeterReadingsHistoryFound.builder()
                .user(request.getUser())
                .meterReadings(meterReadings).build());

        return responseFactory.create(meterReadings);
    }
}
