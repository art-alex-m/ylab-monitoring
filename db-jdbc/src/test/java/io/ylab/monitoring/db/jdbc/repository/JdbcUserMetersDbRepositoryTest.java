package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.service.JdbcTestHelperFactory;
import io.ylab.monitoring.db.jdbc.service.TestConnection;
import io.ylab.monitoring.db.jdbc.service.TestDatabaseExtension;
import io.ylab.monitoring.domain.core.model.Meter;
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
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(TestDatabaseExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcUserMetersDbRepositoryTest {

    private final static JdbcTestHelperFactory testFactory = new JdbcTestHelperFactory();

    private final SqlQueryRepository queryRepository = new SqlQueryResourcesRepository();

    @TestConnection
    private Connection connection;

    private JdbcUserMetersDbRepository sut;

    public static Stream<Arguments> givenMeterName_whenFindByName_thenExpected() {
        return Stream.of(
                Arguments.of(testFactory.testMeterName1, testFactory.testMeter),
                Arguments.of("undefined", null)
        );
    }

    @BeforeEach
    void setUp() {
        sut = new JdbcUserMetersDbRepository(queryRepository, connection);
    }

    @ParameterizedTest
    @MethodSource
    @Order(100)
    void givenMeterName_whenFindByName_thenExpected(String meterName, Meter expected) {
        Optional<Meter> result = sut.findByName(meterName);

        Optional<Meter> expectedOptional = Optional.ofNullable(expected);
        assertThat(result.isPresent()).isEqualTo(expectedOptional.isPresent());
        if (result.isEmpty() || expectedOptional.isEmpty()) return;
        Meter resultMeter = result.get();
        assertThat(resultMeter.getName()).isEqualTo(expected.getName());
        assertThat(resultMeter.getId()).isEqualTo(expected.getId());
    }

    @Test
    @Order(1)
    void givenMethodCall_whenFindAll_thenSuccess() {
        List<Meter> result = sut.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    @Order(1000)
    void givenEmptyDb_whenFindAll_thenSuccess() throws SQLException {
        connection.createStatement().execute("delete from meter_readings");
        connection.createStatement().execute("delete from meters");

        List<Meter> result = sut.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    @Order(101)
    void givenMeter_whenStore_thenSuccess() {
        Meter meter = new CoreMeter(UUID.randomUUID(), "some-test-name");

        boolean result = sut.store(meter);

        assertThat(result).isTrue();
    }

    @Test
    @Order(102)
    void givenExistMeterName_whenStore_thenException() {
        Meter meter = new CoreMeter(UUID.randomUUID(), testFactory.testMeterName1);

        Throwable result = catchThrowable(() -> sut.store(meter));

        assertThat(result).isInstanceOf(JdbcDbException.class);
    }

    @Test
    @Order(103)
    void givenExistUuid_whenStore_thenException() {
        Meter meter = new CoreMeter(testFactory.testMeter1Id, "some-test-name");

        Throwable result = catchThrowable(() -> sut.store(meter));

        assertThat(result).isInstanceOf(JdbcDbException.class);
    }
}