package io.ylab.monitoring.app.springboot.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppSubmissionMeterReadingsInputResponse implements SubmissionMeterReadingsInputResponse {
    private final MeterReading meterReading;
}
