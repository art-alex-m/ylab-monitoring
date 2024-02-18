package io.ylab.monitoring.app.jakartaee.out;

import io.ylab.monitoring.domain.core.model.Meter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AppMeter implements Meter {
    private final UUID id;

    private final String name;
}
