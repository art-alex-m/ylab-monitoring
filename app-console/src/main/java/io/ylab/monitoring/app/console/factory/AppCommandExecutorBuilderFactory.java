package io.ylab.monitoring.app.console.factory;

import io.ylab.monitoring.app.console.controller.ExitCommandExecutor;
import io.ylab.monitoring.app.console.controller.HelpCommandExecutor;
import io.ylab.monitoring.app.console.controller.UnknownCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommandName;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика генерирования команд для сценариев различных ролей
 */
public class AppCommandExecutorBuilderFactory {
    private final Map<AppCommandName, CommandExecutorBuilder> executorBuilderMap = new HashMap<>();

    public AppCommandExecutorBuilderFactory() {
        initBuilders();
    }

    public CommandExecutorBuilder getBuilder(AppCommandName name) {
        CommandExecutorBuilder builder = executorBuilderMap.get(name);
        if (builder == null) {
            throw new RuntimeException("Unknown builder for command " + name.name);
        }
        return builder;
    }

    private void initBuilders() {
        executorBuilderMap.put(AppCommandName.HELP, (c, i, o) -> new HelpCommandExecutor(o));
        executorBuilderMap.put(AppCommandName.EXIT, (c, i, o) -> new ExitCommandExecutor(o));
        executorBuilderMap.put(AppCommandName.UNKNOWN, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.REGISTRATION, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.LOGIN, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.LOGOUT, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.METERS_LIST, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.READING_SUBMIT, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.READING_ACTUAL, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.READING_MONTH, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.READING_HISTORY, (c, i, o) -> new UnknownCommandExecutor());
        executorBuilderMap.put(AppCommandName.AUDIT_LOG, (c, i, o) -> new UnknownCommandExecutor());
    }
}
