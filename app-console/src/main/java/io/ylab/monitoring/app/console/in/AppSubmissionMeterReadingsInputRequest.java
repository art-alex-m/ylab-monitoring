package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Builder
public class AppSubmissionMeterReadingsInputRequest implements SubmissionMeterReadingsInputRequest {
    private final DomainUser user;
    private final String meterName;
    private final Instant period;
    private final long value;
}
