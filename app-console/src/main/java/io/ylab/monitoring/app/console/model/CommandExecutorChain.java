package io.ylab.monitoring.app.console.model;

/**
 * Интерфейс цепочки команд
 */
public interface CommandExecutorChain {
    /**
     * Исполняет полученную команду
     *
     * @param command объект
     */
    void execute(AppCommand command);

    /**
     * Описание команды
     *
     * @return объект
     */
    AppCommand getSignature();

    /**
     * Следующий исполнитель команды в цепочке
     *
     * @return объект или нулл
     */
    CommandExecutorChain getNext();

    /**
     * Устанавливает следующий исполнитель команды
     *
     * @param command объект
     * @return следующий исполнитель
     */
    CommandExecutorChain setNext(CommandExecutorChain command);

    /**
     * Описание исполнителя команды, выводимое в консоль для подсказки
     *
     * @return строка
     */
    String getDescription();

    /**
     * Проверяет есть ли следующий исполнитель команды в цепочке
     *
     * @return истина или ложь
     */
    boolean hasNext();
}
