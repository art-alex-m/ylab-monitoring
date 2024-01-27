package io.ylab.monitoring.app.console.factory;

import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.app.console.model.CommandExecutorChain;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

import java.io.PrintStream;

@FunctionalInterface
public interface CommandExecutorBuilder {
    CommandExecutorChain build(AppUserContext userContext, MonitoringInput interactor, PrintStream out);
}
