package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AbstractInteractorConfig {
    private final Map<AppCommandName, MonitoringInput> interactors = new HashMap<>();


    protected void put(AppCommandName name, MonitoringInput monitoringInput) {
        interactors.put(name, monitoringInput);
    }
}
