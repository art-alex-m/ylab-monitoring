package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.*;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер команды просмотра меню команд
 * <p>
 * Следует с него начинать построение цепочки
 * </p>
 */
public class HelpCommandExecutor extends AbstractCommandExecutor {

    public HelpCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        this(out);
    }

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
