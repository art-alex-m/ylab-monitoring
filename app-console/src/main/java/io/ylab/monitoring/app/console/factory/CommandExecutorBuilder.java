package io.ylab.monitoring.app.console.factory;

import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.app.console.model.CommandExecutorChain;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

import java.io.PrintStream;

/**
 * Интерфейс построителя команд для сценариев ядра
 */
@FunctionalInterface
public interface CommandExecutorBuilder {
    /**
     * Команда-обработчик для переданного сценария ядра
     *
     * @param userContext контекст пользователя
     * @param interactor  сценарий ядра
     * @param out         PrintStream для вывода результатов обработки
     * @return CommandExecutorChain
     */
    CommandExecutorChain build(AppUserContext userContext, MonitoringInput interactor, PrintStream out);
}
