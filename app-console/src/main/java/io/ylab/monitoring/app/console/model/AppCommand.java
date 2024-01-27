package io.ylab.monitoring.app.console.model;

import java.util.Arrays;
import java.util.List;

public class AppCommand {
    private final List<String> operands;

    public AppCommand() {
        this.operands = List.of("");
    }

    public AppCommand(List<String> operands) {
        this.operands = operands;
    }

    public String getStatement() {
        return operands.get(0);
    }

    public List<String> getOperands() {
        return operands;
    }

    @Override
    public String toString() {
        return String.join(" ", operands);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(operands.toArray());
    }
}
