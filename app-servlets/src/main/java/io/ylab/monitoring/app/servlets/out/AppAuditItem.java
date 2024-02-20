package io.ylab.monitoring.app.servlets.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import io.ylab.monitoring.domain.core.model.DomainUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppAuditItem implements AuditItem {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Instant occurredAt;

    private DomainUser user;

    private String name;
}
