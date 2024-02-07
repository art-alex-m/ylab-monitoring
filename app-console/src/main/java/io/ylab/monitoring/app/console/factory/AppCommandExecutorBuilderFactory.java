package io.ylab.monitoring.app.console.factory;

import io.ylab.monitoring.app.console.controller.AuditLogCommandExecutor;
import io.ylab.monitoring.app.console.controller.ExitCommandExecutor;
import io.ylab.monitoring.app.console.controller.HelpCommandExecutor;
import io.ylab.monitoring.app.console.controller.LoginCommandExecutor;
import io.ylab.monitoring.app.console.controller.LogoutCommandExecutor;
import io.ylab.monitoring.app.console.controller.MeterListCommandExecutor;
import io.ylab.monitoring.app.console.controller.ReadingActualCommandExecutor;
import io.ylab.monitoring.app.console.controller.ReadingHistoryCommandExecutor;
import io.ylab.monitoring.app.console.controller.ReadingMonthCommandExecutor;
import io.ylab.monitoring.app.console.controller.ReadingSubmitCommandExecutor;
import io.ylab.monitoring.app.console.controller.ReadingSubmitExtCommandExecutor;
import io.ylab.monitoring.app.console.controller.RegistrationCommandExecutor;
import io.ylab.monitoring.app.console.controller.UnknownCommandExecutor;
import io.ylab.monitoring.app.console.exception.AppUndefinedExecutorBuilderException;
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

    /**
     * По имени возвращает построитель обработчика команды
     *
     * @param name AppCommandName
     * @return CommandExecutorBuilder
     * @throws AppUndefinedExecutorBuilderException Неизвестный построитель
     */
    public CommandExecutorBuilder getBuilder(AppCommandName name) throws AppUndefinedExecutorBuilderException {
        CommandExecutorBuilder builder = executorBuilderMap.get(name);
        if (builder == null) {
            throw new AppUndefinedExecutorBuilderException("Unknown builder for command " + name.name);
        }
        return builder;
    }

    private void initBuilders() {
        executorBuilderMap.put(AppCommandName.HELP, HelpCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.EXIT, ExitCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.UNKNOWN, UnknownCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.REGISTRATION, RegistrationCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.LOGIN, LoginCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.LOGOUT, LogoutCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.METER_LIST, MeterListCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_SUBMIT, ReadingSubmitCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_SUBMIT_EXT, ReadingSubmitExtCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_ACTUAL, ReadingActualCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_MONTH, ReadingMonthCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.READING_HISTORY, ReadingHistoryCommandExecutor::new);
        executorBuilderMap.put(AppCommandName.AUDIT_LOG, AuditLogCommandExecutor::new);
    }
}
