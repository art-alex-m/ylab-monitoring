package io.ylab.monitoring.core.in;

import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class CoreViewMetersInputRequest implements ViewMetersInputRequest {
    private final DomainUser user;
}
