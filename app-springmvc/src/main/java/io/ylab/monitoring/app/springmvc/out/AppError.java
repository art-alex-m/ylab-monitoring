package io.ylab.monitoring.app.springmvc.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Представление ошибки сервиса
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "AppError")
public class AppError {
    private String className;

    private String field;

    private String message;
}
