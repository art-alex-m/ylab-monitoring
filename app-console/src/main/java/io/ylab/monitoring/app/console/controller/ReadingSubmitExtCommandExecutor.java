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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Контроллер подачи показаний счетчиков c возможностью ввода месяца и года
 */
public class ReadingSubmitExtCommandExecutor extends AbstractCommandExecutor {
    private final SubmissionMeterReadingsInput interactor;

    private final AppUserContext userContext;

    public ReadingSubmitExtCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(
                        List.of(AppCommandName.READING_SUBMIT_EXT.name, "<meter name>", "<value int>", "<month>", "<year>")),
                "Submit new meter reading by type, month and year", out);
        this.interactor = (SubmissionMeterReadingsInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        operandSizeValidator(command);

        int readingValue;
        int month;
        int year;
        Instant period;

        try {
            readingValue = Integer.parseInt(command.getOperandAt(2));
            year = Integer.parseInt(command.getOperandAt(4));
            month = Integer.parseInt(command.getOperandAt(3));

            period = LocalDate.of(year, month, 1)
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }

        out.println(interactor.submit(CoreSubmissionMeterReadingsInputRequest.builder()
                .user(userContext.getUser())
                .meterName(command.getOperandAt(1))
                .value(readingValue)
                .period(period)
                .build()));

        return true;
    }
}
