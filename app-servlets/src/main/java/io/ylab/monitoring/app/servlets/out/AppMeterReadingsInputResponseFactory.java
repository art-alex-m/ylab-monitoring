package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.app.servlets.mapper.MeterReadingAppMeterReadingMapper;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponseFactory;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponseFactory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AppMeterReadingsInputResponseFactory implements GetActualMeterReadingsInputResponseFactory,
        GetMonthMeterReadingsResponseFactory, ViewMeterReadingsHistoryInputResponseFactory {

    @Inject
    private MeterReadingAppMeterReadingMapper mapper;

    @Override
    public AppMeterReadingsInputResponse create(List<MeterReading> meterReadings) {
        return new AppMeterReadingsInputResponse(mapper.from(meterReadings));
    }
}
