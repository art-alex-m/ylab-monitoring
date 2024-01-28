package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppUserRegistrationInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контроллер регистрации нового пользователя
 */
public class RegistrationCommandExecutor extends AbstractCommandExecutor {
    private final UserRegistrationInput interactor;

    public RegistrationCommandExecutor(MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(
                        List.of(AppCommandName.REGISTRATION.name, "<login>", "<password>")),
                "Creates an account in the system",
                out);
        this.interactor = (UserRegistrationInput) interactor;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        operandSizeValidator(command);

        UserRegistrationInputRequest request = new AppUserRegistrationInputRequest(command.getOperands().get(1),
                command.getOperands().get(2));

        interactor.register(request);

        out.println("You have successfully maintained your account. Please /login");

        return true;
    }
}
