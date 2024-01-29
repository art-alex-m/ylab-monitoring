package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class AppSubmissionMeterReadingsInputResponse implements SubmissionMeterReadingsInputResponse {

    private final MeterReading meterReading;

    private final static String SEPARATOR = ", ";

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("Reading saved: ")
                .append(meterReading.getMeter().getName())
                .append(SEPARATOR)
                .append(meterReading.getPeriod())
                .append(SEPARATOR)
                .append(meterReading.getValue());

        return stringBuilder.toString();
    }
}
