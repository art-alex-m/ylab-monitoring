package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.List;

/**
 * Ответ в сценарии "Просмотр перечня возможных показаний"
 */
public interface ViewMetersInputResponse {

    /**
     * Список зарегистрированных типов показаний счетчиков
     *
     * @return Список
     */
    List<? extends Meter> getMeters();
}
