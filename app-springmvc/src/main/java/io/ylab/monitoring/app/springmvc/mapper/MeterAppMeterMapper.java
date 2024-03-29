package io.ylab.monitoring.app.springmvc.mapper;

import io.ylab.monitoring.app.springmvc.out.AppMeter;
import io.ylab.monitoring.domain.core.model.Meter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Преобразование в дто списка типов показаний счетчиков
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MeterAppMeterMapper {
    List<AppMeter> from(List<Meter> meterList);
}
