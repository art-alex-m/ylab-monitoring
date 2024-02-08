package io.ylab.monitoring.app.console.model;

import io.ylab.monitoring.domain.audit.repository.CreateAuditLogInputDbRepository;
import io.ylab.monitoring.domain.audit.repository.ViewAuditLogInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserLoginInputDbRepository;
import io.ylab.monitoring.domain.auth.repository.UserRegistrationInputDbRepository;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.repository.GetActualMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.GetMonthMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputDbRepository;
import io.ylab.monitoring.domain.core.repository.SubmissionMeterReadingsInputMeterDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMeterReadingsHistoryInputDbRepository;
import io.ylab.monitoring.domain.core.repository.ViewMetersInputDbRepository;

import java.util.List;

/**
 * Конфигурация репозиториев
 */
public interface DatabaseConfig {
    /**
     * Установить типы показаний счетчиков
     *
     * @param meterList список типов показаний счетчиков
     * @return DatabaseConfig
     */
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
