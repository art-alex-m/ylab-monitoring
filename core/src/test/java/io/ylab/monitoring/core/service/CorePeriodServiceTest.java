package io.ylab.monitoring.core.service;

import io.ylab.monitoring.domain.core.service.PeriodService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CorePeriodServiceTest {

    public static Stream<Arguments> setFistDayOfMonth() {
        Instant expected = Instant.parse("2023-05-01T00:00:00.00Z");
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 30, 1, 1, 1);

        return Stream.of(
                Arguments.of(
                        dateTime.atZone(ZoneId.of("UTC")).toInstant(),
                        expected),
                Arguments.of(
                        dateTime.atZone(ZoneId.of("Europe/Moscow")).toInstant(),
                        expected)

        );
    }

    @ParameterizedTest
    @MethodSource
    void setFistDayOfMonth(Instant rawPeriod, Instant expected) {
        PeriodService sut = new CorePeriodService();

        Instant result = sut.setFistDayOfMonth(rawPeriod);

        assertThat(result).isEqualTo(expected);
    }
}
