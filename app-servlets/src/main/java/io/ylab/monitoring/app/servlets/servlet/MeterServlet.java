package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreViewMetersInputRequest;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.out.ViewMetersInputResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/meters")
@RolesAllowed("USER")
public class MeterServlet extends HttpServlet {

    private final ViewMetersInput metersInput;

    private final ObjectMapper objectMapper;

    private final AppUserContext userContext;

    public MeterServlet() {
        this.metersInput = AppConfiguration.REGISTRY.viewMetersInteractor();
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.userContext = AppConfiguration.REGISTRY.appUserContext();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ViewMetersInputRequest request = new CoreViewMetersInputRequest(userContext.getCurrentUser(req));
        ViewMetersInputResponse response = metersInput.find(request);

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(response.getMeters()));
    }
}
