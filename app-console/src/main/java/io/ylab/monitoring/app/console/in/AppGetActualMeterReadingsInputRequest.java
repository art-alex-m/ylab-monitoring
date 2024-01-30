package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class AppGetActualMeterReadingsInputRequest implements GetActualMeterReadingsInputRequest {
    private final DomainUser user;
}
