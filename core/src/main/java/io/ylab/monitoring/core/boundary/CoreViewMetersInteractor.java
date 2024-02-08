package io.ylab.monitoring.core.boundary;

import io.ylab.monitoring.core.event.CoreViewMetersEntered;
import io.ylab.monitoring.core.event.CoreViewMetersFound;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
public class CoreViewMetersInteractor implements ViewMetersInput {

    private final ViewMetersInputResponseFactory responseFactory;

    private final ViewMetersInputDbRepository metersDbRepository;

    private final MonitoringEventPublisher eventPublisher;

    @Override
    public ViewMetersInputResponse find(ViewMetersInputRequest request) {
        eventPublisher.publish(CoreViewMetersEntered.builder()
                .request(request)
                .user(request.getUser()).build());

        List<Meter> meters = metersDbRepository.findAll();

        eventPublisher.publish(CoreViewMetersFound.builder()
                .meters(meters)
                .user(request.getUser()).build());

        return responseFactory.create(meters);
    }
}
