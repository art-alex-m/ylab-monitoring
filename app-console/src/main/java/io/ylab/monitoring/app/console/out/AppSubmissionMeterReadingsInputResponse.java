package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AppSubmissionMeterReadingsInputResponse implements SubmissionMeterReadingsInputResponse {

    private final MeterReading meterReading;

    private final String separator = ", ";

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Reading saved: ")
                .append(meterReading.getMeter().getName())
                .append(separator)
                .append(meterReading.getPeriod())
                .append(separator)
                .append(meterReading.getValue());

        return stringBuilder.toString();
    }
}
