package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.*;

import java.io.PrintStream;
import java.util.List;

public class HelpCommandExecutor extends AbstractCommandExecutor {

    public HelpCommandExecutor(PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.HELP.name)), "List of available commands", out);
    }

    @Override
    protected boolean doWork(AppCommand command) {

        AppCommandExecutorChainIterator it = new AppCommandExecutorChainIterator(this);
        out.println("\nAvailable commands:");
        while (it.hasNext()) {
            CommandExecutorChain executor = it.next();
            if (executor.getSignature().getStatement().isEmpty()) {
                continue;
            }
            out.printf("%s%n\t%s%n", executor.getSignature(), executor.getDescription());
        }

        return true;
    }
}
