package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.exception.AppProgramExitException;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;

import java.io.PrintStream;
import java.util.List;

public class ExitCommandExecutor extends AbstractCommandExecutor {

    public ExitCommandExecutor(PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.EXIT.name)), "Stop the program", out);
    }

    @Override
    protected boolean doWork(AppCommand command) {
        out.println("Exit the program");
        throw new AppProgramExitException();
    }
}
