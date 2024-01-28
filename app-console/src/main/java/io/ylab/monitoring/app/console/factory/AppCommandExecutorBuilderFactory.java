package io.ylab.monitoring.app.console.factory;

import io.ylab.monitoring.app.console.controller.*;
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
        executorBuilderMap.put(AppCommandName.REGISTRATION, (c, i, o) -> new RegistrationCommandExecutor(i, o));
        executorBuilderMap.put(AppCommandName.LOGIN, (c, i, o) -> new LoginCommandExecutor(i, o));
        executorBuilderMap.put(AppCommandName.LOGOUT, LogoutCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.METER_LIST, MeterListCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_SUBMIT, ReadingSubmitCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_SUBMIT_EXT, ReadingSubmitExtCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_ACTUAL, ReadingActualCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_MONTH, ReadingMonthCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_HISTORY, ReadingHistoryCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.AUDIT_LOG, (c, i, o) -> new UnknownCommandExecutor());
    }
}
