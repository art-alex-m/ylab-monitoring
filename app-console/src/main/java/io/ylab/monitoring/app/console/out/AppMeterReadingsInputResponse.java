package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.MeterReading;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;

import java.util.List;

public class AppMeterReadingsInputResponse extends AbstractMeterReadingsInputResponse
        implements GetActualMeterReadingsInputResponse, GetMonthMeterReadingsResponse,
        ViewMeterReadingsHistoryInputResponse {
    public AppMeterReadingsInputResponse(List<MeterReading> meterReadings) {
        super(meterReadings);
    }
}
