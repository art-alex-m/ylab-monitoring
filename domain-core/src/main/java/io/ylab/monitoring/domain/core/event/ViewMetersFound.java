package io.ylab.monitoring.domain.core.event;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.List;

public interface ViewMetersFound extends MonitoringEvent {
    /**
     * Список типов показаний счетчиков
     *
     * @return список типов или пустой список
     */
    List<Meter> getMeters();
}
