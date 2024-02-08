package io.ylab.monitoring.core.in;

import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class CoreViewMeterReadingsHistoryInputRequest implements ViewMeterReadingsHistoryInputRequest {
    private final DomainUser user;
}
