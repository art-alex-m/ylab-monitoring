package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppUserLoginInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер идентификации в системе
 */
public class LoginCommandExecutor extends AbstractCommandExecutor {

    private final UserLoginInput interactor;

    public LoginCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        this(interactor, out);
    }

    public LoginCommandExecutor(MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(
                        List.of(AppCommandName.LOGIN.name, "<login>", "<password>")),
                "Make identification in system",
                out);
        this.interactor = (UserLoginInput) interactor;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        operandSizeValidator(command);

        UserLoginInputRequest request = new AppUserLoginInputRequest(command.getOperands().get(1),
                command.getOperands().get(2));

        interactor.login(request);

        out.println("You singed in! New commands available. Print /help");

        return true;
    }
}
