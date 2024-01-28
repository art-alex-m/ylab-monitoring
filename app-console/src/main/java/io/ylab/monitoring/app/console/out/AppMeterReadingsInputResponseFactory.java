package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponseFactory;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponseFactory;

import java.util.List;

public class AppMeterReadingsInputResponseFactory implements GetActualMeterReadingsInputResponseFactory,
        GetMonthMeterReadingsResponseFactory, ViewMeterReadingsHistoryInputResponseFactory {
    @Override
    public AppMeterReadingsInputResponse create(List<MeterReading> meterReadings) {
        return new AppMeterReadingsInputResponse(meterReadings);
    }
}
