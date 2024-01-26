package io.ylab.monitoring.app.console.config;

import io.ylab.monitoring.domain.core.out.*;
import lombok.Getter;

@Getter
public class AppInputResponseFactoryConfig {
    private GetActualMeterReadingsInputResponseFactory actualMeterReadingsInputResponseFactory;
    private GetMonthMeterReadingsResponseFactory monthMeterReadingsResponseFactory;
    private SubmissionMeterReadingsInputResponseFactory submissionMeterReadingsInputResponseFactory;
    private ViewMeterReadingsHistoryInputResponseFactory viewMeterReadingsHistoryInputResponseFactory;
    private ViewMetersInputResponseFactory viewMetersInputResponseFactory;
}
