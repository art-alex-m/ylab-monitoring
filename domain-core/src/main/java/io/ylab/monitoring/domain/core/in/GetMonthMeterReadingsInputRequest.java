package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

import java.time.Instant;

public interface GetMonthMeterReadingsInputRequest {
    DomainUser getUser();

    Instant getPeriod();
}
