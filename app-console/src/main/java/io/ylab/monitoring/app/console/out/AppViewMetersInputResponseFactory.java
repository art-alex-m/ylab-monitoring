package io.ylab.monitoring.app.console.out;

import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponseFactory;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class AppViewMetersInputResponseFactory implements ViewMetersInputResponseFactory {
    /**
     * {@inheritDoc}
     */
    @Override
    public ViewMetersInputResponse create(List<Meter> meterList) {
        return new AppViewMetersInputResponse(meterList);
    }
}
