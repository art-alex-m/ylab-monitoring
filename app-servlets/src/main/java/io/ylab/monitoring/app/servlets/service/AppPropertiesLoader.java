package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.app.servlets.interceptor.TimeProfileLog;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppPropertiesLoader {

    private static final String DEFAULT_FILE_NAME = "application.properties";

    /**
     * Загружает файл настроек application.properties
     *
     * @return Properties
     */
    @TimeProfileLog
    public static Properties loadDefault() {
        return load(DEFAULT_FILE_NAME);
    }

    /**
     * Загружает файл настроек из файла по имени
     *
     * @return Properties
     */
    public static Properties load(String fileName) {
        Path defaultConfigFile = Path.of(fileName);
        Properties appProperties = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream propertiesStream = loader.getResourceAsStream(defaultConfigFile.toString());
            appProperties.load(propertiesStream);
            propertiesStream.close();

            if (Files.isReadable(defaultConfigFile)) {
                System.out.println("Found custom properties file. Applying...");
                propertiesStream = Files.newInputStream(defaultConfigFile);
                appProperties.load(propertiesStream);
                propertiesStream.close();
            }

            return appProperties;
        } catch (IOException ex) {
            throw new IllegalCallerException("Application properties load error", ex);
        }
    }
}
