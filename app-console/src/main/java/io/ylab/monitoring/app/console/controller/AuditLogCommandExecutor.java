package io.ylab.monitoring.app.console.controller;

import io.ylab.monitoring.app.console.in.AppViewAuditLogInputRequest;
import io.ylab.monitoring.app.console.model.AbstractCommandExecutor;
import io.ylab.monitoring.app.console.model.AppCommand;
import io.ylab.monitoring.app.console.model.AppCommandName;
import io.ylab.monitoring.app.console.model.AppUserContext;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.core.bounbary.MonitoringInput;

import java.io.PrintStream;
import java.util.List;

/**
 * Контролер показа записей лога аудита
 */
public class AuditLogCommandExecutor extends AbstractCommandExecutor {
    private final ViewAuditLogInput interactor;

    private final AppUserContext userContext;

    public AuditLogCommandExecutor(AppUserContext userContext, MonitoringInput interactor, PrintStream out) {
        super(new AppCommand(List.of(AppCommandName.AUDIT_LOG.name)), "List audit log records", out);
        this.interactor = (ViewAuditLogInput) interactor;
        this.userContext = userContext;
    }

    @Override
    protected boolean doWork(AppCommand command) {
        out.println(interactor.view(new AppViewAuditLogInputRequest(userContext.getUser())));
        return true;
    }
}
