package io.ylab.monitoring.app.springmvc.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Параметры настройки соединения с БД
 */
@Component
@Getter
public class AppDbProperties {

    private final String username;

    private final String password;

    private final String url;

    @Autowired
    public AppDbProperties(
            @Value("${ylab.monitoring.db.username}") String username,
            @Value("${ylab.monitoring.db.password}") String password,
            @Value("${ylab.monitoring.db.jdbc.url}") String url) {

        this.username = username;
        this.password = password;
        this.url = url;
    }
}
