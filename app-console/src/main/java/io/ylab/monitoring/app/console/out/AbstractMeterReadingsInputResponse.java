package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Базовый класс ответов ядра с логикой форматирования в строку для вывода в консоль
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractMeterReadingsInputResponse {
    private final List<MeterReading> meterReadings;

    private final static String SEPARATOR = " | ";

    private final static String HEADER = "USER_ID | PERIOD | METER_NAME | METER_VALUE";

    @Override
    public String toString() {
        if (meterReadings.isEmpty()) {
            return "No meter readings";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HEADER).append("\n");
        meterReadings.forEach(item -> stringBuilder
                .append(item.getUser().getId())
                .append(SEPARATOR)
                .append(item.getPeriod())
                .append(SEPARATOR)
                .append(item.getMeter().getName())
                .append(SEPARATOR)
                .append(item.getValue())
                .append("\n"));

        return stringBuilder.toString();
    }
}
