package io.ylab.monitoring.app.servlets.exception;

import jakarta.servlet.ServletException;

/**
 * Доступ запрещен
 */
public class AccessDeniedServletException extends ServletException {
    public AccessDeniedServletException(String message) {
        super(message);
    }
}
