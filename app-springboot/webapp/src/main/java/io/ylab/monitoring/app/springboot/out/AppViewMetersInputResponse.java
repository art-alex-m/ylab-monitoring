package io.ylab.monitoring.app.springboot.out;

import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AppViewMetersInputResponse implements ViewMetersInputResponse {
    private final List<AppMeter> meters;
}
