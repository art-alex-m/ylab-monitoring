package io.ylab.monitoring.domain.auth.model;

import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;

public interface AuthUser extends DomainUser {
    String getUsername();

    String getPassword();

    DomainRole getRole();
}
