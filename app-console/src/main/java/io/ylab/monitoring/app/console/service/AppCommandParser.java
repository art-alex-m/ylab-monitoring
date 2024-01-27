package io.ylab.monitoring.app.console.service;

import io.ylab.monitoring.app.console.model.AppCommand;

import java.util.List;

public class AppCommandParser {
    public AppCommand parse(String text) {
        return new AppCommand(List.of(text.split("\s+")));
    }
}
