package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.exception.AppProgramExitException;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.Command;

import java.io.PrintStream;
import java.util.List;

public class ExitCommandExecutor extends AbstractCommandExecutor {

    public ExitCommandExecutor(PrintStream out) {
        super(new Command(List.of(AppCommandName.EXIT.name)), "Завершить программу", out);
    }

    @Override
    protected boolean doWork(Command command) {
        out.println("Программа завершена");
        throw new AppProgramExitException();
    }
}
