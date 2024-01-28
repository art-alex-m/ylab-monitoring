package io.ylab.monitoring.app.console.model;

import io.ylab.monitoring.app.console.factory.AppConsoleApplicationBuilder;
import io.ylab.monitoring.app.console.service.AppCommandParser;
import io.ylab.monitoring.domain.core.model.DomainRole;

import java.util.Map;

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

    public void execute(String textCommand) {
        DomainRole currentExecutorKey = userContext.getRole();
        if (roleExecutors.containsKey(currentExecutorKey)) {
            AppCommand command = commandParser.parse(textCommand);
            roleExecutors.get(currentExecutorKey).execute(command);
            return;
        }
        throw new RuntimeException("Undefined command executor chain for " + currentExecutorKey.name());
    }
}