package io.ylab.monitoring.db.jdbc.service;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.core.model.CoreMeterReading;
import io.ylab.monitoring.db.jdbc.model.JdbcAuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class JdbcTestHelper {
    public final UUID testUserIdA = UUID.fromString("38624aa3-943a-49b9-b010-494e9afc6ba0");

    public final UUID testUserIdB = UUID.fromString("38624aa3-943a-49b9-b010-494e9afc3bb0");

    public final UUID testMeter1Id = UUID.fromString("55524aa3-943a-49b9-b010-494e9afc3bb0");
    public final UUID testMeter2Id = UUID.fromString("55534aa3-943a-49b9-b010-494e9afc3bb0");

    public final String testMeterName1 = "meter1";

    public final String testMeterName2 = "meter2";

    public final JdbcAuthUser testAuthUserA = JdbcAuthUser.builder()
            .id(testUserIdA)
            .role(DomainRole.USER)
            .username("testUserA")
            .password("123")
            .build();

    public final JdbcAuthUser testAuthUserB = JdbcAuthUser.builder()
            .id(testUserIdB)
            .role(DomainRole.USER)
            .username("testUserB")
            .password("123")
            .build();

    public final DomainUser testUserA = new CoreDomainUser(testUserIdA);

    public final DomainUser testUserB = new CoreDomainUser(testUserIdB);

    public final Meter testMeter = new CoreMeter(testMeter1Id, testMeterName1);

    public final Instant testPeriod07 = Instant.parse("2023-07-01T00:00:00.00Z");

    public final Instant testPeriod06 = Instant.parse("2023-06-01T00:00:00.00Z");

    public final Instant testPeriod08 = Instant.parse("2023-08-01T00:00:00.00Z");

    public MeterReading create(DomainUser user, Instant period, String meterName) {
        return CoreMeterReading.builder()
                .period(period)
                .meter(new CoreMeter(testMeter1Id, meterName))
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
}
