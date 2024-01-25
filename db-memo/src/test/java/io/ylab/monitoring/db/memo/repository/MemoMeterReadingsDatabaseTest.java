package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.core.model.MeterReading;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.NavigableMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoMeterReadingsDatabaseTest {
    private final MemoMeterReadingFactory readingFactory = new MemoMeterReadingFactory();

    @Test
    void getDb() {
        MemoMeterReadingsDatabase sut = new MemoMeterReadingsDatabase();

        var result = sut.getDb();

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void store() {
        MeterReading meterReading1 = readingFactory.create();
        UUID userId = meterReading1.getUser().getId();
        String meterName = meterReading1.getMeter().getName();
        MemoMeterReadingsDatabase sut = new MemoMeterReadingsDatabase();
        sut.store(meterReading1);

        MeterReading result = sut.store(meterReading1);

        assertThat(result).isEqualTo(meterReading1);
        assertThat(sut.getDb())
                .isNotEmpty()
                .hasSize(1);
        assertThat(sut.getDb().get(userId))
                .isNotEmpty()
                .hasSize(1);
        assertThat(sut.getDb().get(userId).firstEntry().getValue().get(meterName))
                .isEqualTo(meterReading1);
    }

    @Test
    void read() {
        MeterReading meterReading1 = readingFactory.create();
        UUID userId = meterReading1.getUser().getId();
        String meterName = meterReading1.getMeter().getName();
        MemoMeterReadingsDatabase sut = new MemoMeterReadingsDatabase();
        sut.store(meterReading1);

        NavigableMap<Instant, Map<String, MeterReading>> result = sut.read(userId);

        assertThat(result)
                .isNotEmpty()
                .hasSize(1);
        assertThat(result.firstEntry().getValue())
                .isNotEmpty()
                .hasSize(1);
        assertThat(result.firstEntry().getValue().get(meterName))
                .isEqualTo(meterReading1);
    }
}
