package io.ylab.monitoring.app.springboot.mapper;


import io.ylab.monitoring.app.springboot.out.AppMeterReading;
import io.ylab.monitoring.domain.core.model.MeterReading;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Преобразование в дто объектов показаний счетчиков
 */
@Mapper
public interface MeterReadingAppMeterReadingMapper {
    AppMeterReading from(MeterReading meterReading);

    List<AppMeterReading> from(List<MeterReading> meterReadingList);
}
