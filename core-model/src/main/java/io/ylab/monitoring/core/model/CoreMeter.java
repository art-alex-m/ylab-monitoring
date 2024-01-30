package io.ylab.monitoring.core.model;

import io.ylab.monitoring.domain.core.model.Meter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CoreMeter implements Meter {
    private final UUID id;

    private final String name;
}
