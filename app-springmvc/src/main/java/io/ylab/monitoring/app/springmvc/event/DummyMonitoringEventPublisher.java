package io.ylab.monitoring.app.springmvc.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Заглушка для механизма событий ядра
 */
@Component
public class DummyMonitoringEventPublisher implements MonitoringEventPublisher {
    @Override
    public boolean publish(MonitoringEvent event) {
        return true;
    }
}
