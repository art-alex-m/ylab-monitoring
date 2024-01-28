package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.core.model.MeterReading;

import java.time.Instant;
import java.util.*;

/**
 * Хранилище показаний пользователей
 */
public class MemoMeterReadingsDatabase {
    private final Map<UUID, NavigableMap<Instant, Map<String, MeterReading>>> db = new HashMap<>();

    /**
     * Получить все хранилище данных
     *
     * @return база всех показаний
     */
    Map<UUID, NavigableMap<Instant, Map<String, MeterReading>>> getDb() {
        return db;
    }

    /**
     * Сохранить показание счетчика
     *
     * @param meterReading Показание счетчика
     * @return сохраненные показания
     */
    MeterReading store(MeterReading meterReading) {

        UUID userId = meterReading.getUser().getId();
        NavigableMap<Instant, Map<String, MeterReading>> userReadings;
        Map<String, MeterReading> instantReadings;

        userReadings = db.computeIfAbsent(userId, t -> new TreeMap<>(Comparator.reverseOrder()));

        instantReadings = userReadings.computeIfAbsent(meterReading.getPeriod(), k -> new HashMap<>());

        return instantReadings.putIfAbsent(meterReading.getMeter().getName(), meterReading);
    }

    /**
     * Получить показания счетчиков для пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Сортированную карту или null
     */
    NavigableMap<Instant, Map<String, MeterReading>> read(UUID userId) {
        return db.get(userId);
    }
}
