package io.ylab.monitoring.app.springmvc.config;

import jakarta.servlet.SessionTrackingMode;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.util.Set;

public class AppSecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    /**
     * Отключение сессий
     */
    @Override
    protected Set<SessionTrackingMode> getSessionTrackingModes() {
        return Set.of();
    }
}
