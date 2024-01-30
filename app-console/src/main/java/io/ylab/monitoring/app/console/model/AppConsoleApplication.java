package io.ylab.monitoring.app.console.model;

import io.ylab.monitoring.app.console.exception.AppUndefinedCommandExecutorException;
import io.ylab.monitoring.app.console.factory.AppConsoleApplicationBuilder;
import io.ylab.monitoring.app.console.service.AppCommandParser;
import io.ylab.monitoring.domain.core.model.DomainRole;

import java.util.Map;

/**
 * Контекст консольного приложения
 */
public class AppConsoleApplication {
    private final AppCommandParser commandParser;

    private final AppUserContext userContext;

    private final Map<DomainRole, CommandExecutorChain> roleExecutors;


    public AppConsoleApplication(AppCommandParser commandParser, AppUserContext userContext,
            Map<DomainRole, CommandExecutorChain> roleExecutors) {
        this.commandParser = commandParser;
        this.userContext = userContext;
        this.roleExecutors = roleExecutors;
    }

    public static AppConsoleApplicationBuilder builder() {
        return new AppConsoleApplicationBuilder();
    }

    /**
     * Выполняет текстовую команду
     *
     * @param textCommand текст
     * @throws AppUndefinedCommandExecutorException когда нет подходящего исполнителя команды
     */
    public void execute(String textCommand) throws AppUndefinedCommandExecutorException {
        DomainRole currentExecutorKey = userContext.getRole();
        if (roleExecutors.containsKey(currentExecutorKey)) {
            AppCommand command = commandParser.parse(textCommand);
            roleExecutors.get(currentExecutorKey).execute(command);
            return;
        }
        throw new AppUndefinedCommandExecutorException(
                "Undefined command executor chain for " + currentExecutorKey.name());
    }
}
