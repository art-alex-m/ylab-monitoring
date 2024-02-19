package io.ylab.monitoring.app.servlets.support;

import io.ylab.monitoring.app.servlets.out.AppMeter;
import io.ylab.monitoring.app.servlets.out.AppMeterReading;
import io.ylab.monitoring.auth.out.AuthUserLoginInputResponse;
import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

public class TestServletUtils {

    public final static UserLoginInputResponse principal = new AuthUserLoginInputResponse(
            UUID.randomUUID(), DomainRole.USER);

    public final static DomainUser domainUser = new CoreDomainUser(principal.getId());

    public final static AppMeterReading testReading = new AppMeterReading(domainUser, Instant.now(),
            new AppMeter(UUID.randomUUID(), "test-meter"), 2L, UUID.randomUUID(), Instant.now());

    public final static String mediaType = "application/json";

    public final static String encoding = StandardCharsets.UTF_8.toString();

    public final static String testToken = "test-token";

    public final static String testMonth = "2";

    public final static String testYear = "2024";
}
