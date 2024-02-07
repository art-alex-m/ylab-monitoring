package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.core.in.CoreSubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;

import java.io.PrintStream;
import java.time.Instant;
import java.util.List;

/**
 * Контроллер подачи показаний счетчиков
 */
public class ReadingSubmitCommandExecutor extends AbstractCommandExecutor {
    private final SubmissionMeterReadingsInput interactor;

    private final AppUserContext userContext;

    public ReadingSubmitCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.READING_SUBMIT.name, "<meter name>", "<value int>")),
                "Submit new meter reading by type for current month", out);
        this.interactor = (SubmissionMeterReadingsInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        operandSizeValidator(command);

        int readingValue;
        try {
            readingValue = Integer.parseInt(command.getOperandAt(2));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }

        out.println(interactor.submit(CoreSubmissionMeterReadingsInputRequest.builder()
                .user(userContext.getUser())
                .meterName(command.getOperandAt(1))
                .value(readingValue)
                .period(Instant.now())
                .build()));

        return true;
    }
}
