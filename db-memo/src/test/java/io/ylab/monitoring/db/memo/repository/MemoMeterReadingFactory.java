package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.core.model.CoreMeterReading;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

public class MemoMeterReadingFactory {

    public final UUID testUserIdA = UUID.randomUUID();

    public final UUID testUserIdB = UUID.randomUUID();

    public final UUID testMeterId = UUID.randomUUID();

    public final String testMeterName1 = "meter1";

    public final String testMeterName2 = "meter2";

    public final DomainUser testUserA = new CoreDomainUser(testUserIdA);

    public final DomainUser testUserB = new CoreDomainUser(testUserIdB);

    public final Meter testMeter = new CoreMeter(testMeterId, testMeterName1);

    public final Instant testPeriod07 = Instant.parse("2023-07-01T00:00:00.00Z");

    public final Instant testPeriod06 = Instant.parse("2023-06-01T00:00:00.00Z");

    public final Instant testPeriod08 = Instant.parse("2023-08-01T00:00:00.00Z");

    public MeterReading create(DomainUser user, Instant period, String meterName) {
        return CoreMeterReading.builder()
                .period(period)
                .meter(new CoreMeter(testMeterId, meterName))
                .value(1L)
                .user(user)
                .build();
    }

    public MeterReading create(Instant period, String meterName) {
        return create(testUserA, period, meterName);
    }

    public MeterReading create(String meterName) {
        return create(testPeriod07, meterName);
    }

    public MeterReading create() {
        return create(testMeterName1);
    }

    public MemoMeterReadingsDatabase createTestDb() {
        MemoMeterReadingsDatabase database = new MemoMeterReadingsDatabase();

        Stream.of(testUserA, testUserB)
                .map(u -> create(u, testPeriod07, testMeterName1))
                .forEach(database::store);

        database.store(create(testUserA, testPeriod07, testMeterName2));
        database.store(create(testUserA, testPeriod08, testMeterName1));

        return database;
    }
}
