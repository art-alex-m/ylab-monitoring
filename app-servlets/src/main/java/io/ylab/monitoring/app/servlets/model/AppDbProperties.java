package io.ylab.monitoring.app.servlets.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Параметры настройки соединения с БД
 */
@Getter
public class AppDbProperties {

    private final String username;

    private final String password;

    private final String url;

    @Autowired
    public AppDbProperties(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }
}
