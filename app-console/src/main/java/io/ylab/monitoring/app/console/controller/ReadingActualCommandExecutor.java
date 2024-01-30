package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppGetActualMeterReadingsInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контролер показа актуальных показаний счетчиков
 */
public class ReadingActualCommandExecutor extends AbstractCommandExecutor {
    private final GetActualMeterReadingsInput interactor;

    private final AppUserContext userContext;

    public ReadingActualCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.READING_ACTUAL.name)), "List actual readings", out);
        this.interactor = (GetActualMeterReadingsInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {
        out.println(interactor.find(new AppGetActualMeterReadingsInputRequest(userContext.getUser())));
        return true;
    }
}
