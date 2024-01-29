package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;

/**
 * {@inheritDoc}
 */
public class AppSubmissionMeterReadingsInputResponseFactory implements SubmissionMeterReadingsInputResponseFactory {
    /**
     * {@inheritDoc}
     */
    @Override
    public SubmissionMeterReadingsInputResponse create(MeterReading meterReading) {
        return new AppSubmissionMeterReadingsInputResponse(meterReading);
    }
}
