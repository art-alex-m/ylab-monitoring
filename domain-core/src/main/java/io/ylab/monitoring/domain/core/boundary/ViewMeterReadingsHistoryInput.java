package io.ylab.monitoring.domain.core.boundary;

import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;

public interface ViewMeterReadingsHistoryInput {
    ViewMeterReadingsHistoryInputResponse find(ViewMeterReadingsHistoryInputRequest request);
}
