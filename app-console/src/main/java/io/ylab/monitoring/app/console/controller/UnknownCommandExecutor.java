package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.Command;

public class UnknownCommandExecutor extends AbstractCommandExecutor {
    public UnknownCommandExecutor() {
        super(new Command(), "Если команда не была обработана", null);
    }

    @Override
    protected boolean doWork(Command command) {
        throw new IllegalCallerException(String.format("Неизвестная команда '%s'", command));
    }

    @Override
    protected boolean commandNotMatch(Command command) {
        return false;
    }
}
