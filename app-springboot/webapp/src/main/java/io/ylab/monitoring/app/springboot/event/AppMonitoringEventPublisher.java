package io.ylab.monitoring.app.springboot.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@AllArgsConstructor
public class AppMonitoringEventPublisher implements MonitoringEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean publish(MonitoringEvent event) {
        eventPublisher.publishEvent(event);
        return true;
    }
}
