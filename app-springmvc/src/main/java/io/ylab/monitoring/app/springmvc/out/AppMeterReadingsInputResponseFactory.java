package io.ylab.monitoring.app.springmvc.out;

import io.ylab.monitoring.app.springmvc.mapper.MeterReadingAppMeterReadingMapper;
import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponseFactory;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponseFactory;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponseFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppMeterReadingsInputResponseFactory implements GetActualMeterReadingsInputResponseFactory,
        GetMonthMeterReadingsResponseFactory, ViewMeterReadingsHistoryInputResponseFactory {

    private final MeterReadingAppMeterReadingMapper mapper;

    @Override
    public AppMeterReadingsInputResponse create(List<MeterReading> meterReadings) {
        return new AppMeterReadingsInputResponse(mapper.from(meterReadings));
    }
}
