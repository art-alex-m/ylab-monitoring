package io.ylab.monitoring.app.springboot.out;

import io.ylab.monitoring.app.springboot.mapper.MeterAppMeterMapper;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppViewMetersInputResponseFactory implements ViewMetersInputResponseFactory {

    private final MeterAppMeterMapper mapper;

    @Override
    public ViewMetersInputResponse create(List<Meter> meterList) {
        return new AppViewMetersInputResponse(mapper.from(meterList));
    }
}
