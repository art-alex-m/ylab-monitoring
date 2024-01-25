package io.ylab.monitoring.domain.core.out;

import io.ylab.monitoring.domain.core.model.Meter;

import java.util.List;

/**
 * Фабрика по созданию ответов в сценарии "Просмотр перечня возможных показаний"
 */
public interface ViewMetersInputResponseFactory {
    ViewMetersInputResponse create(List<Meter> meterList);
}
