package io.ylab.monitoring.app.servlets.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Представление ошибки сервиса
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppError {
    private String className;

    private String field;

    private String message;
}
