package io.ylab.monitoring.core.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;

import java.time.Instant;

/**
 * Реализация базовой логики классов событий
 */
@Getter
public abstract class CoreMonitoringEvent implements MonitoringEvent {
    private final DomainUser user;

    private final Instant createdAt;

    protected CoreMonitoringEvent(DomainUser user, Instant createdAt) {
        this.user = user;
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        this.createdAt = createdAt;
    }
}
