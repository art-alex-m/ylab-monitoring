package io.ylab.monitoring.app.jakartaee.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Представление ошибки бизнес-логики
 */
@AllArgsConstructor
@Getter
public class AppViolationError {
    private final String className;

    private final String field;

    private final String message;
}
