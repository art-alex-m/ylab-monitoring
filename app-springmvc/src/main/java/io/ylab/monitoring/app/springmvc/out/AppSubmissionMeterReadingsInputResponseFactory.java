package io.ylab.monitoring.app.springmvc.out;

import io.ylab.monitoring.app.springmvc.mapper.MeterReadingAppMeterReadingMapper;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponseFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppSubmissionMeterReadingsInputResponseFactory implements SubmissionMeterReadingsInputResponseFactory {

    private final MeterReadingAppMeterReadingMapper mapper;

    @Override
    public SubmissionMeterReadingsInputResponse create(MeterReading meterReading) {
        return new AppSubmissionMeterReadingsInputResponse(mapper.from(meterReading));
    }
}
