package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.core.in.CoreViewMetersInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер просмотра списка типов показаний счетчиков
 */
public class MeterListCommandExecutor extends AbstractCommandExecutor {

    private final ViewMetersInput interactor;

    private final AppUserContext userContext;

    public MeterListCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.METER_LIST.name)), "List registered meters", out);
        this.interactor = (ViewMetersInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {
        out.println(interactor.find(new CoreViewMetersInputRequest(userContext.getUser())));
        return true;
    }
}
