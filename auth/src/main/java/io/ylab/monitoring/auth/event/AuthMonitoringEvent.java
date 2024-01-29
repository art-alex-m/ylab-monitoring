package io.ylab.monitoring.auth.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class AuthMonitoringEvent implements MonitoringEvent {
    private final DomainUser user;

    private final Instant createdAt;

    protected AuthMonitoringEvent(DomainUser user, Instant createdAt) {
        this.user = user;
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        this.createdAt = createdAt;
    }
}
