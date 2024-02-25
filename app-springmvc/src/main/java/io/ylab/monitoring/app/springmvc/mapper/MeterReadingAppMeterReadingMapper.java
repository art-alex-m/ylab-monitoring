package io.ylab.monitoring.app.springmvc.mapper;

import io.ylab.monitoring.app.springmvc.out.AppMeterReading;
import io.ylab.monitoring.domain.core.model.MeterReading;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import java.util.List;

/**
 * Преобразование в дто объектов показаний счетчиков
 */
@Mapper(componentModel = ComponentModel.SPRING)
public interface MeterReadingAppMeterReadingMapper {
    AppMeterReading from(MeterReading meterReading);

    List<AppMeterReading> from(List<MeterReading> meterReadingList);
}
