package io.ylab.monitoring.app.servlets.servlet;

import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/logout")
public class LogoutServlet extends HttpServlet {

    private final AuthTokenManager tokenManager;

    public LogoutServlet() {
        this.tokenManager = AppConfiguration.REGISTRY.authTokenManager();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authHeader = req.getHeader("Authorization");
        tokenManager.revokeToken(authHeader);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
