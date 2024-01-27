package io.ylab.monitoring.app.console;

import io.ylab.monitoring.app.console.exception.AppProgramExitException;
import io.ylab.monitoring.app.console.model.AppConsoleApplication;
import io.ylab.monitoring.domain.core.exception.MonitoringException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppConsoleApplication application = AppConsoleApplication.builder()
                .withDefaultAdmin()
                .withMeter("gaz")
                .withMeter("electro")
                .withMeter("teplo")
                .build();

        System.out.println("Welcome!");
        System.out.println("Type /help to see available commands");

        Scanner scanner = new Scanner(System.in);

        while (!Thread.interrupted()) {
            System.out.println("Input new command");
            try {
                String commandText = scanner.nextLine();
                application.execute(commandText);
            } catch (AppProgramExitException ex) {
                break;
            } catch (IllegalArgumentException | IllegalCallerException | MonitoringException ex) {
                System.out.println(ex.getMessage());
            }
        }

        scanner.close();
    }
}
