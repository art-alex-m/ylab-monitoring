package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppUserLogoutInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.auth.boundary.UserLogoutInput;

import java.io.PrintStream;
import java.util.List;

public class LogoutCommandExecutor extends AbstractCommandExecutor {

    private final AppUserContext userContext;
    private final UserLogoutInput interactor;

    public LogoutCommandExecutor(AppUserContext userContext, UserLogoutInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.LOGOUT.name)), "Sign Out", out);
        this.interactor = interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {

        interactor.logout(new AppUserLogoutInputRequest(userContext.getUser()));

        out.println("You have successfully logged out.");

        return true;
    }
}
