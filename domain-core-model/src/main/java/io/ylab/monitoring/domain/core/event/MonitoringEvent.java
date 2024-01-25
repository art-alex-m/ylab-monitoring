package io.ylab.monitoring.domain.core.event;

import java.time.Instant;

public interface MonitoringEvent {
    Instant getCreatedAt();


}
