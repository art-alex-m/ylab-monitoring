package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.ViewMetersEntered;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CoreViewMetersEntered extends CoreMonitoringEvent implements ViewMetersEntered {
    private static final String EVENT_NAME = "Enter in use case 'view meters'";

    private final ViewMetersInputRequest request;

    @Builder
    public CoreViewMetersEntered(DomainUser user, Instant createdAt, ViewMetersInputRequest request) {
        super(user, createdAt);
        this.request = request;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
