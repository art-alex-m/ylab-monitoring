package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class AbstractMeterReadingsInputResponse {
    private final List<MeterReading> meterReadings;

    private final String separator = " | ";

    private final String header = "USER_ID | PERIOD | METER_NAME | METER_VALUE";

    @Override
    public String toString() {
        if (meterReadings.isEmpty()) {
            return "No meter readings";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(header).append("\n");
        meterReadings.forEach(item -> stringBuilder
                .append(item.getUser().getId())
                .append(separator)
                .append(item.getPeriod())
                .append(separator)
                .append(item.getMeter().getName())
                .append(separator)
                .append(item.getValue())
                .append("\n"));

        return stringBuilder.toString();
    }
}
