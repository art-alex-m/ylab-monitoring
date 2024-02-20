package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppSubmitReadingRequest;
import io.ylab.monitoring.app.servlets.service.AppUserContext;
import io.ylab.monitoring.app.servlets.service.AppValidationService;
import io.ylab.monitoring.core.in.CoreSubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.core.in.CoreViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.out.SubmissionMeterReadingsInputResponse;
import io.ylab.monitoring.domain.core.out.ViewMeterReadingsHistoryInputResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/readings")
@RolesAllowed("USER")
@AllArgsConstructor
public class UserReadingsServlet extends HttpServlet {

    private final ObjectMapper objectMapper;

    private final ViewMeterReadingsHistoryInput historyInput;

    private final SubmissionMeterReadingsInput submissionInput;

    private final AppUserContext userContext;

    private final AppValidationService validationService;


    public UserReadingsServlet() {
        this.historyInput = AppConfiguration.REGISTRY.meterReadingsHistoryInteractor();
        this.submissionInput = AppConfiguration.REGISTRY.submissionMeterReadingsInteractor();
        this.objectMapper = AppConfiguration.REGISTRY.objectMapper();
        this.userContext = AppConfiguration.REGISTRY.appUserContext();
        this.validationService = AppConfiguration.REGISTRY.appValidationService();
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppSubmitReadingRequest appRequest = objectMapper.readValue(req.getInputStream(),
                AppSubmitReadingRequest.class);
        validationService.validate(appRequest);

        SubmissionMeterReadingsInputRequest request = CoreSubmissionMeterReadingsInputRequest.builder()
                .meterName(appRequest.getMeterName())
                .user(userContext.getCurrentUser(req))
                .value(appRequest.getValue())
                .period(appRequest.getPeriod())
                .build();
        SubmissionMeterReadingsInputResponse response = submissionInput.submit(request);

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.getWriter().print(objectMapper.writeValueAsString(response.getMeterReading()));
    }
}
