package io.ylab.monitoring.app.springboot.out;

import io.swagger.v3.oas.annotations.media.Schema;
import io.ylab.monitoring.domain.core.model.Meter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Schema(name = "Meter")
public class AppMeter implements Meter {
    private final UUID id;

    @Schema(description = "Meter name", example = "teplo")
    private final String name;
}
