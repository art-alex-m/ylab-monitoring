package io.ylab.monitoring.app.console.model;

import java.io.PrintStream;

public abstract class AbstractCommandExecutor implements CommandExecutorChain {

    protected final PrintStream out;

    private final AppCommand signature;

    private final String description;

    private CommandExecutorChain next;

    protected AbstractCommandExecutor(AppCommand signature, String description, PrintStream out) {
        this.signature = signature;
        this.description = description;
        this.out = out;
    }

    protected abstract boolean doWork(AppCommand command);

    @Override
    public void execute(AppCommand command) {
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
    public AppCommand getSignature() {
        return signature;
    }

    @Override
    public String getDescription() {
        return description;
    }

    protected boolean commandNotMatch(AppCommand command) {
        return !signature.getStatement().equals(command.getStatement());
    }

    protected void operandSizeValidator(AppCommand command) {
        if (command.getOperands().size() != getSignature().getOperands().size()) {
            throw new IllegalArgumentException("Insufficient data");
        }
    }
}
