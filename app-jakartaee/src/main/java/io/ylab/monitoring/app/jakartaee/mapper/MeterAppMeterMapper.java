package io.ylab.monitoring.app.jakartaee.mapper;

import io.ylab.monitoring.app.jakartaee.out.AppMeter;
import io.ylab.monitoring.domain.core.model.Meter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Преобразование в дто списка типов показаний счетчиков
 */
@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface MeterAppMeterMapper {
    List<AppMeter> from(List<Meter> meterList);
}
