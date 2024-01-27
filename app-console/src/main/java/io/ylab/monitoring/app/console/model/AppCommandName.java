package io.ylab.monitoring.app.console.model;

/**
 * Перечень команд системы
 */
public enum AppCommandName {
    HELP("/help"),
    REGISTRATION("/registration"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    METERS_LIST("/meters-list"),
    READING_SUBMIT("/reading-submit"),
    READING_ACTUAL("/reading-actual"),
    READING_MONTH("/reading-month"),
    READING_HISTORY("/reading-history"),
    AUDIT_LOG("/audit-log"),
    EXIT("/exit"),
    UNKNOWN(null);

    public final String name;

    AppCommandName(String name) {
        this.name = name;
    }
}
