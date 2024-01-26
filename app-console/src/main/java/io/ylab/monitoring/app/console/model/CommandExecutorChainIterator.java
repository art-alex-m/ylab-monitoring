package io.ylab.monitoring.app.console.model;

import java.util.Iterator;

public class CommandExecutorChainIterator implements Iterator<CommandExecutorChain> {
    protected CommandExecutorChain current;

    public CommandExecutorChainIterator(CommandExecutorChain head) {
        this.current = head;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public CommandExecutorChain next() {
        CommandExecutorChain commandExecutorChain = current;
        current = commandExecutorChain.getNext();

        return commandExecutorChain;
    }
}
