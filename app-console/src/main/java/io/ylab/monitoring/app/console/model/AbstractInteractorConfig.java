package io.ylab.monitoring.app.console.model;

import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Базовая логика конфигурирования сценариев ядра
 */
@Getter
public abstract class AbstractInteractorConfig {
    private final Set<Entry> interactors = new LinkedHashSet<>();


    protected void put(AppCommandName name, MonitoringInput monitoringInput) {
        interactors.add(new Entry(name, monitoringInput));
    }

    @AllArgsConstructor
    @Getter
    public static class Entry {
        private final AppCommandName name;
        private final MonitoringInput interactor;

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
