package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/admin/readings")
@RolesAllowed("ADMIN")
public class AdminReadingsServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    private final ViewMeterReadingsHistoryInput historyInput;

    private final AppUserContext userContext;


    public AdminReadingsServlet() {
        this.historyInput = AppConfiguration.REGISTRY.adminMeterReadingsHistoryInteractor();
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.userContext = AppConfiguration.REGISTRY.appUserContext();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ViewMeterReadingsHistoryInputRequest request = new CoreViewMeterReadingsHistoryInputRequest(
                userContext.getCurrentUser(req));
        ViewMeterReadingsHistoryInputResponse response = historyInput.find(request);

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(response.getMeterReadings()));
    }
}
