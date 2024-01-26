package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.*;

import java.util.List;

public interface DatabaseConfig {
    DatabaseConfig setMeters(List<Meter> meterList);

    GetActualMeterReadingsInputDbRepository getUserActualMeterReadingsInputDbRepository();

    GetMonthMeterReadingsInputDbRepository getUserMonthMeterReadingsInputDbRepository();

    ViewMeterReadingsHistoryInputDbRepository getUserViewMeterReadingsHistoryInputDbRepository();

    GetActualMeterReadingsInputDbRepository getAdminActualMeterReadingsInputDbRepository();

    GetMonthMeterReadingsInputDbRepository getAdminMonthMeterReadingsInputDbRepository();

    ViewMeterReadingsHistoryInputDbRepository getAdminViewMeterReadingsHistoryInputDbRepository();

    SubmissionMeterReadingsInputMeterDbRepository getSubmissionMeterReadingsInputMeterDbRepository();

    SubmissionMeterReadingsInputDbRepository getSubmissionMeterReadingsInputDbRepository();

    ViewMetersInputDbRepository getViewMetersInputDbRepository();

    UserLoginInputDbRepository getUserLoginInputDbRepository();

    UserRegistrationInputDbRepository getUserRegistrationInputDbRepository();

    CreateAuditLogInputDbRepository getCreateAuditLogInputDbRepository();

    ViewAuditLogInputDbRepository getViewAuditLogInputDbRepository();
}
