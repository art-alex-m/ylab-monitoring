package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AppViewMeterReadingsHistoryInputRequest implements ViewMeterReadingsHistoryInputRequest {
    private final DomainUser user;
}
