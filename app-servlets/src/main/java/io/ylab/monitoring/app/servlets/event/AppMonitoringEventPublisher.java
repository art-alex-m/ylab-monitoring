package io.ylab.monitoring.app.servlets.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Реализация событийного программирования
 */
@ApplicationScoped
@Named("appEventPublisher")
@Default
public class AppMonitoringEventPublisher implements MonitoringEventPublisher {

    @Inject
    private Event<MonitoringEvent> enterpriseEvent;

    @Override
    public boolean publish(MonitoringEvent event) {
        enterpriseEvent.fire(event);
        return true;
    }
}
