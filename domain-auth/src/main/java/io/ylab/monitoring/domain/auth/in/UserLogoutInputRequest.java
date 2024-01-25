package io.ylab.monitoring.domain.auth.in;

import io.ylab.monitoring.domain.core.model.DomainUser;

public interface UserLogoutInputRequest {
    DomainUser getUser();
}
