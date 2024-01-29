package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.ViewMetersFound;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class CoreViewMetersFound extends CoreMonitoringEvent implements ViewMetersFound {
    private static final String EVENT_NAME = "Finish use case 'view meters'";

    private final List<Meter> meters;

    @Builder
    public CoreViewMetersFound(DomainUser user, Instant createdAt, List<Meter> meters) {
        super(user, createdAt);
        this.meters = meters;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }
}
