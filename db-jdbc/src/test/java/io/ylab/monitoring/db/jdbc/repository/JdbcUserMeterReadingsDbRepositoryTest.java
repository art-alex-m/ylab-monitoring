package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.service.JdbcTestHelper;
import io.ylab.monitoring.db.jdbc.service.TestConnection;
import io.ylab.monitoring.db.jdbc.service.TestDatabaseExtension;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import io.ylab.monitoring.domain.core.model.MeterReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(TestDatabaseExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcUserMeterReadingsDbRepositoryTest {

    private final SqlQueryRepository queryRepository = new SqlQueryResourcesRepository();

    @TestConnection
    private Connection connection;

    private JdbcUserMeterReadingsDbRepository sut;

    public static Stream<Arguments> givenUserPeriodMeter_whenExistsByUserAndPeriodAndMeter_thenExpected() {
        Meter testMeter1 = JdbcTestHelper.testMeter;
        Meter testMeter2 = new CoreMeter(JdbcTestHelper.testMeter2Id, JdbcTestHelper.testMeterName2);

        return Stream.of(
                Arguments.of(JdbcTestHelper.testUserA, JdbcTestHelper.testPeriod07, testMeter1, true),
                Arguments.of(JdbcTestHelper.testUserA, JdbcTestHelper.testPeriod07, testMeter2, true),
                Arguments.of(JdbcTestHelper.testUserB, JdbcTestHelper.testPeriod07, testMeter1, true),
                Arguments.of(JdbcTestHelper.testUserB, JdbcTestHelper.testPeriod06, testMeter2, false),
                Arguments.of(JdbcTestHelper.testUserA, JdbcTestHelper.testPeriod06, testMeter1, false),
                Arguments.of(JdbcTestHelper.testUserB, JdbcTestHelper.testPeriod06, testMeter1, false)
        );
    }

    public static Stream<Arguments> givenUserPeriod_whenFindByUserAndPeriod_thenExpected() {
        return Stream.of(
                Arguments.of(JdbcTestHelper.testUserA, JdbcTestHelper.testPeriod07, 2),
                Arguments.of(JdbcTestHelper.testUserA, JdbcTestHelper.testPeriod06, 0),
                Arguments.of(JdbcTestHelper.testUserB, JdbcTestHelper.testPeriod06, 0),
                Arguments.of(JdbcTestHelper.testUserA, JdbcTestHelper.testPeriod08, 1),
                Arguments.of(JdbcTestHelper.testUserB, JdbcTestHelper.testPeriod07, 1)
        );
    }

    public static Stream<Arguments> givenDuplicatedMeterReading_whenSave_thenException() {
        return Stream.of(
                Arguments.of(JdbcTestHelper.create()),
                Arguments.of(JdbcTestHelper.create(JdbcTestHelper.testMeterName2))
        );
    }

    @BeforeEach
    void setUp() {
        sut = new JdbcUserMeterReadingsDbRepository(queryRepository, connection);
    }

    @Order(100)
    @Test
    void givenUserA_whenFindActualByUser_thenExpected() {
        List<MeterReading> result = sut.findActualByUser(JdbcTestHelper.testUserA);

        assertThat(result).isNotEmpty().hasSize(1);
        MeterReading resultReading = result.get(0);
        assertThat(resultReading.getMeter().getName()).isEqualTo(JdbcTestHelper.testMeterName1);
        assertThat(resultReading.getMeter().getId()).isEqualTo(JdbcTestHelper.testMeter1Id);
        assertThat(resultReading.getPeriod()).isEqualTo(JdbcTestHelper.testPeriod08);
        assertThat(resultReading.getId()).isNotNull();
        assertThat(resultReading.getUser()).isNotNull();
        assertThat(resultReading.getUser().getId()).isEqualTo(JdbcTestHelper.testUserIdA);
        assertThat(resultReading.getValue()).isEqualTo(118);
        assertThat(resultReading.getCreatedAt()).isNotNull();
    }

    @Order(100)
    @ParameterizedTest
    @MethodSource
    void givenUserPeriodMeter_whenExistsByUserAndPeriodAndMeter_thenExpected(DomainUser user, Instant period,
            Meter meter, boolean expected) {
        boolean result = sut.existsByUserAndPeriodAndMeter(user, period, meter);

        assertThat(result).isEqualTo(expected);
    }

    @Order(1000)
    @ParameterizedTest
    @MethodSource
    void givenDuplicatedMeterReading_whenSave_thenException(MeterReading reading) {
        Throwable result = catchThrowable(() -> sut.save(reading));

        assertThat(result).isInstanceOf(JdbcDbException.class).hasMessageContaining("duplicate key value violates");
    }

    @Order(1000)
    @Test
    void givenMeterReading_whenSave_thenSuccess() {
        MeterReading reading = JdbcTestHelper.create(JdbcTestHelper.testPeriod06, JdbcTestHelper.testMeterName1);

        boolean result = sut.save(reading);

        assertThat(result).isTrue();
    }

    @Order(100)
    @Test
    void givenUserA_whenFindByUser_thenSuccess() {
        List<MeterReading> result = sut.findByUser(JdbcTestHelper.testUserA);

        assertThat(result)
                .hasSize(3)
                .map(MeterReading::getPeriod)
                .isEqualTo(
                        List.of(JdbcTestHelper.testPeriod08, JdbcTestHelper.testPeriod07, JdbcTestHelper.testPeriod07));
    }

    @Order(100)
    @ParameterizedTest
    @MethodSource
    void givenUserPeriod_whenFindByUserAndPeriod_thenExpected(DomainUser user, Instant period, int expectedSize) {
        List<MeterReading> result = sut.findByUserAndPeriod(user, period);

        assertThat(result).hasSize(expectedSize);
    }
}