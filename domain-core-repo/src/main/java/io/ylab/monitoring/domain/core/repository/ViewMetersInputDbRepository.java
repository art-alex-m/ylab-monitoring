package io.ylab.monitoring.domain.core.repository;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.List;

/**
 * Репозиторий хранения типов показаний счетчиков в сценарии "Просмотр перечня возможных показаний"
 */
public interface ViewMetersInputDbRepository {

    /**
     * Список типов показаний счетчиков
     *
     * @return список типов или пустой список
     */
    List<Meter> findAll();
}
