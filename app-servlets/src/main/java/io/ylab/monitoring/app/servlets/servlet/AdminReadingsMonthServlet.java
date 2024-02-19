package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppMonthReadingRequest;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreGetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.GetMonthMeterReadingsResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/admin/readings/month")
@RolesAllowed("ADMIN")
public class AdminReadingsMonthServlet extends HttpServlet {
    private final ObjectMapper objectMapper;

    private final AppUserContext userContext;

    private final GetMonthMeterReadingsInput monthInput;

    public AdminReadingsMonthServlet() {
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.userContext = AppConfiguration.REGISTRY.appUserContext();
        this.monthInput = AppConfiguration.REGISTRY.adminMonthMeterReadingsInteractor();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppMonthReadingRequest appRequest = new AppMonthReadingRequest(req);
        GetMonthMeterReadingsInputRequest request = new CoreGetMonthMeterReadingsInputRequest(
                userContext.getCurrentUser(req), appRequest.getPeriod());
        GetMonthMeterReadingsResponse response = monthInput.find(request);

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(response.getMeterReadings()));
    }
}
