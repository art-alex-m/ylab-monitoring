package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер просмотра всех показаний счетчиков
 */
public class ReadingHistoryCommandExecutor extends AbstractCommandExecutor {
    private final ViewMeterReadingsHistoryInput interactor;

    private final AppUserContext userContext;

    public ReadingHistoryCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.READING_HISTORY.name)), "List all readings", out);
        this.interactor = (ViewMeterReadingsHistoryInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {
        out.println(interactor.find(new AppViewMeterReadingsHistoryInputRequest(userContext.getUser())));
        return true;
    }
}
