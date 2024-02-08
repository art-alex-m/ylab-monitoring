package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.app.console.out.AppMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.console.out.AppSubmissionMeterReadingsInputResponseFactory;
import io.ylab.monitoring.app.console.out.AppViewAuditLogInputResponseFactory;
import io.ylab.monitoring.app.console.out.AppViewMetersInputResponseFactory;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponseFactory;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponseFactory;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponseFactory;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;
import lombok.Getter;

/**
 * Конфигуратор фабрик ответов сценариев ядра
 */
@Getter
public class AppInputResponseFactoryConfig {
    private GetActualMeterReadingsInputResponseFactory actualMeterReadingsInputResponseFactory;
    private GetMonthMeterReadingsResponseFactory monthMeterReadingsResponseFactory;
    private SubmissionMeterReadingsInputResponseFactory submissionMeterReadingsInputResponseFactory;
    private ViewMeterReadingsHistoryInputResponseFactory viewMeterReadingsHistoryInputResponseFactory;
    private ViewMetersInputResponseFactory viewMetersInputResponseFactory;
    private ViewAuditLogInputResponseFactory viewAuditLogInputResponseFactory;

    public AppInputResponseFactoryConfig() {
        initFactories();
    }

    private void initFactories() {
        AppMeterReadingsInputResponseFactory readingsInputResponseFactory = new AppMeterReadingsInputResponseFactory();
        actualMeterReadingsInputResponseFactory = readingsInputResponseFactory;
        monthMeterReadingsResponseFactory = readingsInputResponseFactory;
        viewMeterReadingsHistoryInputResponseFactory = readingsInputResponseFactory;
        submissionMeterReadingsInputResponseFactory = new AppSubmissionMeterReadingsInputResponseFactory();
        viewMetersInputResponseFactory = new AppViewMetersInputResponseFactory();
        viewAuditLogInputResponseFactory = new AppViewAuditLogInputResponseFactory();
    }
}
