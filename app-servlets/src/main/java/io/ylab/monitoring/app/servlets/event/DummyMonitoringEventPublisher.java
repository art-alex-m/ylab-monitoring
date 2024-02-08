package io.ylab.monitoring.app.servlets.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;

public class DummyMonitoringEventPublisher implements MonitoringEventPublisher {
    @Override
    public boolean publish(MonitoringEvent event) {
        return true;
    }
}
