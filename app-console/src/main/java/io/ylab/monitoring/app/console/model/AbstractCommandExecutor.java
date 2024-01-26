package io.ylab.monitoring.app.console.model;

import java.io.PrintStream;

public abstract class AbstractCommandExecutor implements CommandExecutorChain {

    protected final PrintStream out;
    private final Command signature;
    private final String description;
    private CommandExecutorChain next;

    protected AbstractCommandExecutor(Command signature, String description, PrintStream out) {
        this.signature = signature;
        this.description = description;
        this.out = out;
    }

    protected abstract boolean doWork(Command command);

    @Override
    public void execute(Command command) {
        if ((commandNotMatch(command) || !doWork(command)) && hasNext()) {
            next.execute(command);
        }
    }

    @Override
    public CommandExecutorChain getNext() {
        return next;
    }

    @Override
    public CommandExecutorChain setNext(CommandExecutorChain next) {
        this.next = next;
        return next;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Command getSignature() {
        return signature;
    }

    @Override
    public String getDescription() {
        return description;
    }

    protected boolean commandNotMatch(Command command) {
        return !signature.getStatement().equals(command.getStatement());
    }
}
