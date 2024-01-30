package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.List;

/**
 * Фабрика по созданию ответов в сценарии "Просмотр перечня возможных показаний"
 */
public interface ViewMetersInputResponseFactory {
    /**
     * Создает объект ответа для сценария "Просмотр перечня возможных показаний"
     *
     * @param meterList список типов показаний счетчиков
     * @return список
     */
    ViewMetersInputResponse create(List<Meter> meterList);
}
