package io.ylab.monitoring.app.console.model;

/**
 * Перечень команд системы
 */
public enum AppCommandName {
    HELP("/help"),
    REGISTRATION("/register"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    METER_LIST("/meter-list"),
    READING_SUBMIT("/reading-submit"),
    READING_SUBMIT_EXT("/reading-submit-ext"),
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
