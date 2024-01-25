package io.ylab.monitoring.domain.core.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

public interface GetActualMeterReadingsInputRequest {
    DomainUser getUser();
}
