package io.ylab.monitoring.app.servlets.model;

import lombok.Getter;

/**
 * Параметры настройки соединения с БД
 */
@Getter
public class AppDbProperties {

    private final String username;

    private final String password;

    private final String url;

    public AppDbProperties(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }
}
