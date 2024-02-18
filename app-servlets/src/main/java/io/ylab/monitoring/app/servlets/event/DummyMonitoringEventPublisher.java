package io.ylab.monitoring.app.servlets.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;

/**
 * Заглушка для механизма событий ядра
 */
public class DummyMonitoringEventPublisher implements MonitoringEventPublisher {
    @Override
    public boolean publish(MonitoringEvent event) {
        return true;
    }
}
