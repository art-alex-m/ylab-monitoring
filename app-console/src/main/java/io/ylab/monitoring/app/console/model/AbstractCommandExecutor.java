package io.ylab.monitoring.app.console.model;

import java.io.PrintStream;


/**
 * {@inheritDoc}
 */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(AppCommand command) {
        if ((commandNotMatch(command) || !doWork(command)) && hasNext()) {
            next.execute(command);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandExecutorChain getNext() {
        return next;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandExecutorChain setNext(CommandExecutorChain next) {
        this.next = next;
        return next;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return next != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppCommand getSignature() {
        return signature;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Проверяет может ли обработчик выполнить команду
     *
     * @param command AppCommand
     * @return истина, если команда не может быть выполнена
     */
    protected boolean commandNotMatch(AppCommand command) {
        return !signature.getStatement().equals(command.getStatement());
    }

    /**
     * Проверяет совпадают ли сигнатуры команды и исполнителя
     *
     * @param command AppCommand
     * @throws IllegalArgumentException если сигнатуры не совпадают
     */
    protected void operandSizeValidator(AppCommand command) {
        if (command.getOperands().size() != getSignature().getOperands().size()) {
            throw new IllegalArgumentException("Insufficient data");
        }
    }
}
