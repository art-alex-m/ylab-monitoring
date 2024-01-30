package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppGetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;

import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
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

        int year;
        int month;
        Instant period;

        try {
            year = Integer.parseInt(command.getOperandAt(2));
            month = Integer.parseInt(command.getOperandAt(1));

            period = LocalDate.of(year, month, 1)
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }

        out.println(interactor.find(new AppGetMonthMeterReadingsInputRequest(userContext.getUser(), period)));

        return true;
    }
}
