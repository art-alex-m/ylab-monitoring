package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppLoginRequest;
import io.ylab.monitoring.app.servlets.out.AppAuthToken;
import io.ylab.monitoring.app.servlets.service.AppValidationService;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/login")
@PermitAll
@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    private final UserLoginInput loginInput;

    private final AuthTokenManager tokenManager;

    private final AppValidationService validationService;

    public LoginServlet() {
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.loginInput = AppConfiguration.REGISTRY.userLoginInteractor();
        this.tokenManager = AppConfiguration.REGISTRY.authTokenManager();
        this.validationService = AppConfiguration.REGISTRY.appValidationService();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppLoginRequest loginRequest = objectMapper.readValue(req.getInputStream(), AppLoginRequest.class);
        validationService.validate(loginRequest);

        UserLoginInputResponse response = loginInput.login(loginRequest);
        AppAuthToken token = new AppAuthToken(tokenManager.createToken(response));

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(token));
    }
}
