package io.ylab.monitoring.app.console.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;

public class AppMonitoringEventPublisher implements MonitoringEventPublisher {

    @Override
    public boolean publish(MonitoringEvent event) {
        return true;
    }
}
