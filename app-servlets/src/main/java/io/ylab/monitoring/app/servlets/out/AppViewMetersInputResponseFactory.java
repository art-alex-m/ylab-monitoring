package io.ylab.monitoring.app.servlets.out;


import io.ylab.monitoring.app.servlets.mapper.MeterAppMeterMapper;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class AppViewMetersInputResponseFactory implements ViewMetersInputResponseFactory {

    private final MeterAppMeterMapper mapper;

    @Override
    public ViewMetersInputResponse create(List<Meter> meterList) {
        return new AppViewMetersInputResponse(mapper.from(meterList));
    }
}
