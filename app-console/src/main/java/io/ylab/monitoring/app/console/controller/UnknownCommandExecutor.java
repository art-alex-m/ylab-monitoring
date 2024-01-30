package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

import java.io.PrintStream;

/**
 * Обработчик неизвестной команды
 * <p>
 * Следует ставить последним в цепочку
 * </p>
 */
public class UnknownCommandExecutor extends AbstractCommandExecutor {

    public UnknownCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        this();
    }

    public UnknownCommandExecutor() {
        super(new AppCommand(), "If command was not processed", null);
    }

    @Override
    protected boolean doWork(AppCommand command) {
        throw new IllegalCallerException(String.format("Unknown command '%s'", command));
    }

    @Override
    protected boolean commandNotMatch(AppCommand command) {
        return false;
    }
}
