package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreGetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.out.GetActualMeterReadingsInputResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/readings/actual")
@RolesAllowed("USER")
public class UserReadingsActualServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    private final AppUserContext userContext;

    private final GetActualMeterReadingsInput actualInput;

    public UserReadingsActualServlet() {
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.actualInput = AppConfiguration.REGISTRY.actualMeterReadingsInteracror();
        this.userContext = AppConfiguration.REGISTRY.appUserContext();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GetActualMeterReadingsInputRequest request = new CoreGetActualMeterReadingsInputRequest(
                userContext.getCurrentUser());
        GetActualMeterReadingsInputResponse response = actualInput.find(request);

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(response.getMeterReadings()));
    }
}
