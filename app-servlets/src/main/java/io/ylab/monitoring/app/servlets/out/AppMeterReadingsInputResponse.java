package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AppMeterReadingsInputResponse implements GetActualMeterReadingsInputResponse,
        GetMonthMeterReadingsResponse, ViewMeterReadingsHistoryInputResponse {

    private final List<AppMeterReading> meterReadings;
}
