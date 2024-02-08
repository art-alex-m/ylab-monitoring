package io.ylab.monitoring.core.in;

import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class CoreGetMonthMeterReadingsInputRequest implements GetMonthMeterReadingsInputRequest {
    private final DomainUser user;
    private final Instant period;
}
