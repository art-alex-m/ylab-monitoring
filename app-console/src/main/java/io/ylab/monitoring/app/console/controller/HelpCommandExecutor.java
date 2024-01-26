package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.*;

import java.io.PrintStream;
import java.util.List;

public class HelpCommandExecutor extends AbstractCommandExecutor {

    public HelpCommandExecutor(PrintStream out) {
        super(new Command(List.of(AppCommandName.HELP.name)), "Вывод списка доступных команд", out);
    }

    @Override
    protected boolean doWork(Command command) {

        CommandExecutorChainIterator it = new CommandExecutorChainIterator(this);
        out.println("\nДоступные команды:");
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
