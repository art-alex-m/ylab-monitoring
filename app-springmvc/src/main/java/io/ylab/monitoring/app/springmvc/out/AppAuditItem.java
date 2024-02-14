package io.ylab.monitoring.app.springmvc.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class AppAuditItem implements AuditItem {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private final Instant occurredAt;

    private final DomainUser user;

    private final String name;
}
