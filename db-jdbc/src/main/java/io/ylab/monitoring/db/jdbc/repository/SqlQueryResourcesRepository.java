package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Осуществляет доступ и кеширование SQL запросов из текстовых файлов в classpath
 */
public class SqlQueryResourcesRepository implements SqlQueryRepository {
    private final Map<String, String> sqlTemplates = new ConcurrentHashMap<>();

    @Override
    public String getSql(String fileName) {
        if (sqlTemplates.containsKey(fileName)) {
            return sqlTemplates.get(fileName);
        }

        try {
            URL resourceUrl = getClass().getClassLoader().getResource(fileName);
            Objects.requireNonNull(resourceUrl);
            try (InputStream is = resourceUrl.openStream()) {
                String sql = new String(is.readAllBytes());
                sqlTemplates.put(fileName, sql);
                return sql;
            }
        } catch (Exception ex) {
            throw new JdbcDbException(ex);
        }
    }
}
