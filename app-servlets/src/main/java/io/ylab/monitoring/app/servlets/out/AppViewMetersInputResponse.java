package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AppViewMetersInputResponse implements ViewMetersInputResponse {
    private final List<Meter> meters;
}
