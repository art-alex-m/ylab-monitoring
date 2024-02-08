package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AppViewMetersInputResponseFactory implements ViewMetersInputResponseFactory {
    @Override
    public ViewMetersInputResponse create(List<Meter> meterList) {
        return new AppViewMetersInputResponse(
                meterList.stream()
                        .map(meter -> new AppMeter(meter.getId(), meter.getName()))
                        .map(meter -> (Meter) meter)
                        .toList());
    }
}
