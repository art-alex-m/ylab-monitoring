package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppUserLoginInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер идентификации в системе
 */
public class LoginCommandExecutor extends AbstractCommandExecutor {

    private final UserLoginInput interactor;

    public LoginCommandExecutor(UserLoginInput interactor, PrintStream out) {
        super(new AppCommand(
                        List.of(AppCommandName.LOGIN.name, "<login>", "<password>")),
                "Make identification in system",
                out);
        this.interactor = interactor;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        if (command.getOperands().size() != getSignature().getOperands().size()) {
            throw new IllegalArgumentException("Insufficient data");
        }

        UserLoginInputRequest request = new AppUserLoginInputRequest(command.getOperands().get(1),
                command.getOperands().get(2));

        interactor.login(request);

        out.println("You singed in! New commands available. Print /help");

        return true;
    }
}
