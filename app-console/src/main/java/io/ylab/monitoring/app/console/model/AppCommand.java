package io.ylab.monitoring.app.console.model;

import java.util.Arrays;
import java.util.List;

/**
 * Конфигурация консольной команды для исполнения
 */
public class AppCommand {
    private final List<String> operands;

    public AppCommand() {
        this.operands = List.of("");
    }

    public AppCommand(List<String> operands) {
        this.operands = operands;
    }

    /**
     * Возвращает имя команды
     *
     * @return строка
     */
    public String getStatement() {
        return operands.get(0);
    }

    /**
     * Полный список операндов команды, включая имя команды под аргументом 0
     * @return список строк
     */
    public List<String> getOperands() {
        return operands;
    }

    /**
     * Возвращает аргумент команды по индексу
     *
     * @param index Индекс аргумента
     * @return строка
     */
    public String getOperandAt(int index) {
        return operands.get(index);
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
