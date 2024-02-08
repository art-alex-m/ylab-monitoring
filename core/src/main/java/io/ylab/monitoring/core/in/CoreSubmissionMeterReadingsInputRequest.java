package io.ylab.monitoring.core.in;

import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * {@inheritDoc}
 */
@AllArgsConstructor
@Getter
@Builder
public class CoreSubmissionMeterReadingsInputRequest implements SubmissionMeterReadingsInputRequest {
    private final DomainUser user;
    private final String meterName;
    private final Instant period;
    private final long value;
}
