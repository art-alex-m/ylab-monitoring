package io.ylab.monitoring.app.console.model;

/**
 * Интерфейс цепочки команд
 */
public interface CommandExecutorChain {
    void execute(AppCommand command);

    AppCommand getSignature();

    CommandExecutorChain getNext();

    CommandExecutorChain setNext(CommandExecutorChain command);

    String getDescription();

    boolean hasNext();
}
