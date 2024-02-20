package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.out.AppError;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * Представление ошибки в json формате
 */
@WebServlet("/json-exception-handler")
@PermitAll
public class JsonExceptionHandlerServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    public JsonExceptionHandlerServlet() {
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processException(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processException(req, resp);
    }

    private void processException(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Throwable throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Integer statusCode = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String) req.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        AppError error = new AppError(throwable.getClass().getName(), "message", errorMessage);
        resp.setStatus(statusCode > 0 ? statusCode : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(error));
    }
}
