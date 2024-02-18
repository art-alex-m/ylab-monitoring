package io.ylab.monitoring.app.servlets.out;

import io.ylab.monitoring.app.servlets.mapper.MeterAppMeterMapper;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AppViewMetersInputResponseFactory implements ViewMetersInputResponseFactory {

    @Inject
    private MeterAppMeterMapper mapper;

    @Override
    public ViewMetersInputResponse create(List<Meter> meterList) {
        return new AppViewMetersInputResponse(mapper.from(meterList));
    }
}
