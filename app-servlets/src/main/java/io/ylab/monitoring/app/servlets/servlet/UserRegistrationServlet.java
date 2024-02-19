package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppRegistrationRequest;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Регистрация пользователей
 */
@WebServlet("/api/register")
@PermitAll
public class UserRegistrationServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    private final UserRegistrationInput registrationInput;

    public UserRegistrationServlet() {
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.registrationInput = AppConfiguration.REGISTRY.userRegistrationInteractor();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRegistrationInputRequest appRequest = objectMapper.readValue(req.getInputStream(),
                AppRegistrationRequest.class);
        registrationInput.register(appRequest);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
