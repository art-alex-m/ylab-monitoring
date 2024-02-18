package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppLoginRequest;
import io.ylab.monitoring.app.servlets.out.AppAuthToken;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    private final UserLoginInput loginInput;

    private final AuthTokenManager tokenManager;

    public LoginServlet() {
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.loginInput = AppConfiguration.REGISTRY.userLoginInteractor();
        this.tokenManager = AppConfiguration.REGISTRY.authTokenManager();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppLoginRequest loginRequest = objectMapper.readValue(req.getInputStream(), AppLoginRequest.class);
        UserLoginInputResponse response = loginInput.login(loginRequest);

        AppAuthToken token = new AppAuthToken(tokenManager.createToken(response));

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(token));
    }
}
