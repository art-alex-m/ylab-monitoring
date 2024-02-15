package io.ylab.monitoring.app.springmvc.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Представление ошибки сервиса
 */
@AllArgsConstructor
@Getter
public class AppError {
    private final String className;

    private final String field;

    private final String message;
}
