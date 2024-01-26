package io.ylab.monitoring.app.console.model;

public interface CommandExecutorChain {
    void execute(Command command);

    Command getSignature();

    CommandExecutorChain getNext();

    CommandExecutorChain setNext(CommandExecutorChain command);

    String getDescription();

    boolean hasNext();
}
