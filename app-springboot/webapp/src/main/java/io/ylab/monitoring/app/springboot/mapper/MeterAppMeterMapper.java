package io.ylab.monitoring.app.springboot.mapper;


import io.ylab.monitoring.app.springboot.out.AppMeter;
import io.ylab.monitoring.domain.core.model.Meter;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Преобразование в дто списка типов показаний счетчиков
 */
@Mapper
public interface MeterAppMeterMapper {
    List<AppMeter> from(List<Meter> meterList);
}
