package io.ylab.monitoring.app.console;

import io.ylab.monitoring.app.console.config.AppJdbcDbConfig;
import io.ylab.monitoring.app.console.exception.AppProgramExitException;
import io.ylab.monitoring.app.console.exception.AppPropertiesFileException;
import io.ylab.monitoring.app.console.model.AppConsoleApplication;
import io.ylab.monitoring.db.migrations.service.LiquibaseMigrationService;
import io.ylab.monitoring.domain.core.exception.MonitoringException;

import java.io.FileInputStream;
import java.io.IOException;
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
                .username(username)
                .password(password)
                .build()
                .migrate("!test");

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
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String defaultConfigFile = rootPath + "application.properties";
        Properties appProperties = new Properties();
        try {
            appProperties.load(new FileInputStream(defaultConfigFile));
            return appProperties;
        } catch (IOException ex) {
            throw new AppPropertiesFileException(ex);
        }
    }
}
