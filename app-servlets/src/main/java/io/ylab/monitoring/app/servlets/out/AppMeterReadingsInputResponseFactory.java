package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponseFactory;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponseFactory;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AppMeterReadingsInputResponseFactory implements GetActualMeterReadingsInputResponseFactory,
        GetMonthMeterReadingsResponseFactory, ViewMeterReadingsHistoryInputResponseFactory {
    @Override
    public AppMeterReadingsInputResponse create(List<MeterReading> meterReadings) {
        return new AppMeterReadingsInputResponse(meterReadings.stream()
                .map(reading -> (MeterReading) new AppMeterReading(reading.getUser(), reading.getPeriod(),
                        reading.getMeter(), reading.getValue(), reading.getId(), reading.getCreatedAt()))
                .toList());
    }
}
