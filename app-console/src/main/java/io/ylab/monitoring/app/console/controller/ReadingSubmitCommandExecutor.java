package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер подачи показаний счетчиков
 */
public class ReadingSubmitCommandExecutor extends AbstractCommandExecutor {
    private final SubmissionMeterReadingsInput interactor;

    private final AppUserContext userContext;

    public ReadingSubmitCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.READING_SUBMIT.name, "<meter name>", "<value int>")),
                "Submit new meter reading by type", out);
        this.interactor = (SubmissionMeterReadingsInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        operandSizeValidator(command);

        out.println("Not implemented yet");

        return true;
    }
}
