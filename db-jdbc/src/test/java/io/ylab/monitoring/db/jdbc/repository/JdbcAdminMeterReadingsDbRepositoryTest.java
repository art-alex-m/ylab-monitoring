package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.db.jdbc.service.JdbcTestHelper;
import io.ylab.monitoring.db.jdbc.service.TestConnection;
import io.ylab.monitoring.db.jdbc.service.TestDatabaseExtension;
import io.ylab.monitoring.domain.core.model.DomainUser;
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
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestDatabaseExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcAdminMeterReadingsDbRepositoryTest {

    private final SqlQueryRepository queryRepository = new SqlQueryResourcesRepository();

    private final DomainUser dummyUser = new CoreDomainUser(UUID.randomUUID());

    @TestConnection
    private Connection connection;

    private JdbcAdminMeterReadingsDbRepository sut;

    public static Stream<Arguments> givenPeriodAnyUser_whenAdminFindByUserAndPeriod_thenExpected() {
        return Stream.of(
                Arguments.of(JdbcTestHelper.testPeriod08, 1),
                Arguments.of(JdbcTestHelper.testPeriod07, 3),
                Arguments.of(JdbcTestHelper.testPeriod06, 0)
        );
    }

    @BeforeEach
    void setUp() {
        sut = new JdbcAdminMeterReadingsDbRepository(queryRepository, connection);
    }

    @Test
    void givenAnyUser_whenAdminFindActualByUser_thenSuccess() {
        List<MeterReading> result = sut.findActualByUser(dummyUser);

        assertThat(result)
                .hasSize(2)
                .map(MeterReading::getValue)
                .isEqualTo(List.of(217L, 118L));
    }

    @ParameterizedTest
    @MethodSource
    void givenPeriodAnyUser_whenAdminFindByUserAndPeriod_thenExpected(Instant period, int expectedSize) {
        List<MeterReading> result = sut.findByUserAndPeriod(dummyUser, period);

        assertThat(result).hasSize(expectedSize);
    }

    @Test
    void givenAnyUser_whenFindByUser_thenSuccess() {
        List<MeterReading> result = sut.findByUser(dummyUser);

        assertThat(result)
                .hasSize(4)
                .map(MeterReading::getValue)
                .isEqualTo(List.of(118L, 117L, 217L, 127L));
        ;
    }

    @Test
    @Order(Integer.MAX_VALUE)
    void givenEmptyDb_whenFindByUser_thenSuccess() throws SQLException {
        connection.createStatement().execute("delete from meter_readings");

        List<MeterReading> result = sut.findByUser(dummyUser);

        assertThat(result).isEmpty();
    }
}