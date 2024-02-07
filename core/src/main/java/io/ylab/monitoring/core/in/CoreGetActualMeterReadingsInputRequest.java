package io.ylab.monitoring.core.in;

import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class CoreGetActualMeterReadingsInputRequest implements GetActualMeterReadingsInputRequest {
    private final DomainUser user;
}
