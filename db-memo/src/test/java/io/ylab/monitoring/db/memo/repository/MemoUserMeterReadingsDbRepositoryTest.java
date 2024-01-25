package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MemoUserMeterReadingsDbRepositoryTest {

    private final static MemoMeterReadingFactory testFactory = new MemoMeterReadingFactory();

    private MemoUserMeterReadingsDbRepository sut;

    public static Stream<Arguments> findByUserAndPeriod() {
        return Stream.of(
                Arguments.of(testFactory.testUserA, testFactory.testPeriod07, 2),
                Arguments.of(testFactory.testUserA, testFactory.testPeriod06, 0),
                Arguments.of(testFactory.testUserB, testFactory.testPeriod06, 0),
                Arguments.of(testFactory.testUserA, testFactory.testPeriod08, 1),
                Arguments.of(testFactory.testUserB, testFactory.testPeriod07, 1)
        );
    }

    public static Stream<Arguments> existsByUserAndPeriodAndMeter() {
        Meter testMeter1 = testFactory.testMeter;
        Meter testMeter2 = new CoreMeter(testFactory.testMeterId, testFactory.testMeterName2);

        return Stream.of(
                Arguments.of(testFactory.testUserA, testFactory.testPeriod07, testMeter1, true),
                Arguments.of(testFactory.testUserA, testFactory.testPeriod07, testMeter2, true),
                Arguments.of(testFactory.testUserB, testFactory.testPeriod07, testMeter1, true),
                Arguments.of(testFactory.testUserB, testFactory.testPeriod07, testMeter2, false),
                Arguments.of(testFactory.testUserA, testFactory.testPeriod06, testMeter1, false),
                Arguments.of(testFactory.testUserB, testFactory.testPeriod06, testMeter1, false)
        );
    }

    public static Stream<Arguments> save() {
        return Stream.of(
                Arguments.of(testFactory.create(), false),
                Arguments.of(testFactory.create(testFactory.testPeriod06, testFactory.testMeterName1), true),
                Arguments.of(testFactory.create(testFactory.testMeterName2), false)
        );
    }

    @BeforeEach
    void setUp() {
        sut = new MemoUserMeterReadingsDbRepository(testFactory.createTestDb());
    }

    @Test
    void findActualByUser() {
        List<MeterReading> result = sut.findActualByUser(testFactory.testUserA);

        assertThat(result)
                .isNotEmpty()
                .hasSize(1);
        assertThat(result.get(0).getMeter().getName()).isEqualTo(testFactory.testMeterName1);
        assertThat(result.get(0).getPeriod()).isEqualTo(testFactory.testPeriod08);
    }

    @ParameterizedTest
    @MethodSource
    void findByUserAndPeriod(DomainUser user, Instant period, int expectedSize) {
        List<MeterReading> result = sut.findByUserAndPeriod(user, period);

        assertThat(result).hasSize(expectedSize);
    }

    @ParameterizedTest
    @MethodSource
    void save(MeterReading reading, boolean expected) {
        boolean result = sut.save(reading);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void existsByUserAndPeriodAndMeter(DomainUser user, Instant period, Meter meter, boolean expected) {
        boolean result = sut.existsByUserAndPeriodAndMeter(user, period, meter);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findByUser() {
        List<MeterReading> result = sut.findByUser(testFactory.testUserA);

        assertThat(result)
                .hasSize(3)
                .map(MeterReading::getPeriod)
                .isEqualTo(List.of(testFactory.testPeriod08, testFactory.testPeriod07, testFactory.testPeriod07));
    }
}
