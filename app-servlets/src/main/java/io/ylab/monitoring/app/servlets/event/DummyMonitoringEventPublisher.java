package io.ylab.monitoring.app.servlets.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Named;

/**
 * Заглушка для механизма событий ядра
 */
@ApplicationScoped
@Named("appEventPublisher")
@Alternative
public class DummyMonitoringEventPublisher implements MonitoringEventPublisher {
    @Override
    public boolean publish(MonitoringEvent event) {
        return true;
    }
}
