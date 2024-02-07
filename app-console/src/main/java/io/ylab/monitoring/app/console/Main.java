package io.ylab.monitoring.app.console;

import io.ylab.monitoring.app.console.config.AppJdbcDbConfig;
import io.ylab.monitoring.app.console.exception.AppProgramExitException;
import io.ylab.monitoring.app.console.exception.AppPropertiesFileException;
import io.ylab.monitoring.app.console.model.AppConsoleApplication;
import io.ylab.monitoring.db.migrations.service.LiquibaseMigrationService;
import io.ylab.monitoring.domain.core.exception.MonitoringException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Properties appProperties = loadAppProperties();

        String url = appProperties.getProperty("ylab.monitoring.db.jdbc.url");
        String username = appProperties.getProperty("ylab.monitoring.db.username");
        String password = appProperties.getProperty("ylab.monitoring.db.password");

        LiquibaseMigrationService.builder()
                .url(appProperties.getProperty("ylab.monitoring.db.liquibase.url", url))
                .username(username).password(password).build()
                .migrate("!test", "data");

        AppConsoleApplication application = AppConsoleApplication.builder()
                .withAdmin(appProperties.getProperty("ylab.monitoring.admin.username"),
                        appProperties.getProperty("ylab.monitoring.admin.password"))
                .withMeters(appProperties.getProperty("ylab.monitoring.startup.meters"))
                .withPasswordSalt(appProperties.getProperty("ylab.monitoring.auth.password.salt"))
                .withDatabaseConfig(AppJdbcDbConfig.builder().url(url).password(password).username(username).build())
                .build();

        System.out.println("Welcome to Monitoring Service!");
        System.out.println("Type /help to see available commands");

        Scanner scanner = new Scanner(System.in);

        while (!Thread.interrupted()) {
            System.out.println("Type new command, please");
            try {
                String commandText = scanner.nextLine();
                application.execute(commandText);
            } catch (AppProgramExitException ex) {
                break;
            } catch (IllegalArgumentException | IllegalCallerException | MonitoringException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Загружает файл настроек application.properties
     *
     * @return Properties
     */
    private static Properties loadAppProperties() {
        Path defaultConfigFile = Path.of("application.properties");
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
            throw new AppPropertiesFileException(ex);
        }
    }
}
