package io.ylab.monitoring.app.console.service;

import io.ylab.monitoring.app.console.model.Command;

import java.util.List;

public class CommandParser {
    public Command parse(String text) {
        return new Command(List.of(text.split("\s+")));
    }
}
