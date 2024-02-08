package io.ylab.monitoring.db.jdbc.model;

import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;

@AllArgsConstructor
@Builder
@Getter
public class JdbcAuditItem implements AuditItem {

    public static final String USER = "user_uuid";
    public static final String OCCURRED_AT = "occurred_at";
    public static final String NAME = "name";

    @NonNull
    private final Instant occurredAt;

    @NonNull
    private final DomainUser user;

    @NonNull
    private final String name;
}
