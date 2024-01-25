package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder
@Getter
public abstract class CoreMonitoringEvent implements MonitoringEvent {
    private final DomainUser user;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
