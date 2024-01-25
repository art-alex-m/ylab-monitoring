package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;

public interface GetActualMeterReadingsInput {
    GetActualMeterReadingsInputResponse find(GetActualMeterReadingsInputRequest request);
}
