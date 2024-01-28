package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер просмотра показаний счетчиков за выбранный месяц
 */
public class ReadingMonthCommandExecutor extends AbstractCommandExecutor {
    private final GetMonthMeterReadingsInput interactor;

    private final AppUserContext userContext;

    public ReadingMonthCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.READING_MONTH.name, "<month number>", "<year>")),
                "List readings for month", out);
        this.interactor = (GetMonthMeterReadingsInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        operandSizeValidator(command);

        out.println("Not implemented yet");
        return true;
    }
}
