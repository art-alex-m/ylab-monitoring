package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.MeterReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MemoAdminMeterReadingsDbRepositoryTest {

    private final static MemoTestHelperFactory testFactory = new MemoTestHelperFactory();

    private DomainUser dummyUser;

    private MemoAdminMeterReadingsDbRepository sut;

    public static Stream<Arguments> findByUserAndPeriod() {
        return Stream.of(
                Arguments.of(testFactory.testPeriod08, 1),
                Arguments.of(testFactory.testPeriod07, 3),
                Arguments.of(testFactory.testPeriod06, 0)
        );
    }

    @BeforeEach
    void setUp() {
        dummyUser = new CoreDomainUser(UUID.randomUUID());
        sut = new MemoAdminMeterReadingsDbRepository(testFactory.createTestDb());
    }

    @Test
    void findActualByUser() {
        List<MeterReading> result = sut.findActualByUser(dummyUser);

        assertThat(result).hasSize(2);
    }

    @ParameterizedTest
    @MethodSource
    void findByUserAndPeriod(Instant period, int expectedSize) {
        List<MeterReading> result = sut.findByUserAndPeriod(dummyUser, period);

        assertThat(result).hasSize(expectedSize);
    }

    @Test
    void findByUser() {
        List<MeterReading> result = sut.findByUser(dummyUser);

        assertThat(result).hasSize(4);
    }
}
