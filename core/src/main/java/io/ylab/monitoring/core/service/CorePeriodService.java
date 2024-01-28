package io.ylab.monitoring.core.service;

import io.ylab.monitoring.domain.core.service.PeriodService;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class CorePeriodService implements PeriodService {

    private final ZoneId utcZoneId = ZoneId.of("UTC");

    @Override
    public Instant setFistDayOfMonth(Instant period) {
        return YearMonth.from(period.atZone(utcZoneId))
                .atDay(1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);
    }
}
