package io.ylab.monitoring.app.console.service;

import io.ylab.monitoring.app.console.model.AppCommand;

import java.util.List;

/**
 * Преобразует текстовую команду в программную сущность
 */
public class AppCommandParser {

    /**
     * Преобразует текст в объект AppCommand
     *
     * @param text строка
     * @return объект команды
     */
    public AppCommand parse(String text) {
        return new AppCommand(List.of(text.split("\s+")));
    }
}
