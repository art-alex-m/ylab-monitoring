package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.app.servlets.mapper.MeterReadingAppMeterReadingMapper;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class AppSubmissionMeterReadingsInputResponseFactory implements SubmissionMeterReadingsInputResponseFactory {

    private final MeterReadingAppMeterReadingMapper mapper;

    @Override
    public SubmissionMeterReadingsInputResponse create(MeterReading meterReading) {
        return new AppSubmissionMeterReadingsInputResponse(mapper.from(meterReading));
    }
}
