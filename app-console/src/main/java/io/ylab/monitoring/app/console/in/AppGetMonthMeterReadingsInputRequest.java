package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class AppGetMonthMeterReadingsInputRequest implements GetMonthMeterReadingsInputRequest {
    private final DomainUser user;
    private final Instant period;
}
