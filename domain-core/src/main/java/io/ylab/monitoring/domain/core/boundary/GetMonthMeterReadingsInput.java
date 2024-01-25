package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;

public interface GetMonthMeterReadingsInput {
    GetMonthMeterReadingsResponse find(GetActualMeterReadingsInputRequest request);
}
