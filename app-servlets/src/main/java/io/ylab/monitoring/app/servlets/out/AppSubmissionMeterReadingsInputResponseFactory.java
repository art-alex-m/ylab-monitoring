package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;
import jakarta.inject.Singleton;

@Singleton
public class AppSubmissionMeterReadingsInputResponseFactory implements SubmissionMeterReadingsInputResponseFactory {
    @Override
    public SubmissionMeterReadingsInputResponse create(MeterReading meterReading) {
        return new AppSubmissionMeterReadingsInputResponse(new AppMeterReading(meterReading.getUser(),
                meterReading.getPeriod(), meterReading.getMeter(), meterReading.getValue(), meterReading.getId(),
                meterReading.getCreatedAt()));
    }
}
