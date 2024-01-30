package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.core.model.CoreMeter;
import io.ylab.monitoring.domain.core.model.Meter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoUserMetersDbRepositoryTest {

    private final static MemoTestHelperFactory testFactory = new MemoTestHelperFactory();

    MemoUserMetersDbRepository sut;

    @BeforeEach
    void setUp() {
        sut = new MemoUserMetersDbRepository();
    }

    @Test
    void findByName() {
        sut.store(testFactory.testMeter);

        Optional<Meter> result = sut.findByName(testFactory.testMeterName1);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(testFactory.testMeter);
    }

    @Test
    void findByNameWhenEmptyDatabase() {
        Optional<Meter> result = sut.findByName(testFactory.testMeterName1);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void findAll() {
        List<Meter> result = sut.findAll();

        assertThat(result).hasSize(0);
    }

    @Test
    void findAllWithDoubleStore() {
        sut.store(testFactory.testMeter);
        sut.store(testFactory.testMeter);

        List<Meter> result = sut.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void findAllWithData() {
        sut.store(testFactory.testMeter);
        sut.store(new CoreMeter(UUID.randomUUID(), "some-test-value-1"));
        sut.store(new CoreMeter(UUID.randomUUID(), "some-test-value-2"));
        sut.store(new CoreMeter(UUID.randomUUID(), "some-test-value-2"));

        List<Meter> result = sut.findAll();

        assertThat(result)
                .hasSize(3)
                .map(Meter::getName)
                .usingRecursiveComparison().ignoringCollectionOrder()
                .isEqualTo(List.of(testFactory.testMeterName1, "some-test-value-1", "some-test-value-2"))
        ;
    }
}