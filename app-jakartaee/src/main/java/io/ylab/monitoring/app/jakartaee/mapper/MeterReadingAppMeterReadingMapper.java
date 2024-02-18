package io.ylab.monitoring.app.jakartaee.mapper;

import io.ylab.monitoring.app.jakartaee.out.AppMeterReading;
import io.ylab.monitoring.domain.core.model.MeterReading;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import java.util.List;

/**
 * Преобразование в дто объектов показаний счетчиков
 */
@Mapper(componentModel = ComponentModel.CDI)
public interface MeterReadingAppMeterReadingMapper {
    AppMeterReading from(MeterReading meterReading);

    List<AppMeterReading> from(List<MeterReading> meterReadingList);
}
