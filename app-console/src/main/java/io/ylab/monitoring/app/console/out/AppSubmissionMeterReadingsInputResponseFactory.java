package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;

public class AppSubmissionMeterReadingsInputResponseFactory implements SubmissionMeterReadingsInputResponseFactory {
    @Override
    public SubmissionMeterReadingsInputResponse create(MeterReading meterReading) {
        return new AppSubmissionMeterReadingsInputResponse(meterReading);
    }
}
