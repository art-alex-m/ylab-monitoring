package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.audit.in.AuditViewAuditLogInputRequest;
import io.ylab.monitoring.domain.audit.boundary.ViewAuditLogInput;
import io.ylab.monitoring.domain.audit.out.ViewAuditLogInputResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/admin/audit-logs")
@RolesAllowed("ADMIN")
@AllArgsConstructor
public class AdminAuditServlet extends HttpServlet {
    private final ObjectMapper objectMapper;

    private final AppUserContext userContext;

    private final ViewAuditLogInput auditLogInput;

    public AdminAuditServlet() {
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.userContext = AppConfiguration.REGISTRY.appUserContext();
        this.auditLogInput = AppConfiguration.REGISTRY.adminAuditLogInteractor();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ViewAuditLogInputResponse response = auditLogInput.view(new AuditViewAuditLogInputRequest(
                userContext.getCurrentUser(req)));

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(response.getAuditLog()));
    }
}
