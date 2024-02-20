package io.ylab.monitoring.app.servlets.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Параметры настройки соединения с БД
 */
@Getter
@AllArgsConstructor
public class AppDbProperties {

    private final String username;

    private final String password;

    private final String url;
}
