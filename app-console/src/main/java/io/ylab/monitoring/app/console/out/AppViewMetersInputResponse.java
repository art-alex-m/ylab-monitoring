package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Getter
public class AppViewMetersInputResponse implements ViewMetersInputResponse {
    private final List<Meter> meters;

    private final static String HEADER = "METER_NAME";

    @Override
    public String toString() {
        if (meters.isEmpty()) {
            return "No meters registered";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER).append("\n");
        meters.forEach(item -> stringBuilder.append(item.getName()).append("\n"));

        return stringBuilder.toString();
    }
}
