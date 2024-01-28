package io.ylab.monitoring.app.console.in;

import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AppViewMetersInputRequest implements ViewMetersInputRequest {
    private final DomainUser user;
}