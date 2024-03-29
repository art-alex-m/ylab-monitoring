package io.ylab.monitoring.app.jakartaee.resource;

import io.ylab.monitoring.app.jakartaee.in.AppMonthReadingRequest;
import io.ylab.monitoring.app.jakartaee.in.AppSubmitReadingRequest;
import io.ylab.monitoring.app.jakartaee.interceptor.AuditLogger;
import io.ylab.monitoring.app.jakartaee.interceptor.TimeProfileLog;
import io.ylab.monitoring.app.jakartaee.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreGetActualMeterReadingsInputRequest;
import io.ylab.monitoring.core.in.CoreGetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.core.in.CoreSubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.core.in.CoreViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.SubmissionMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.SubmissionMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.MeterReading;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Обработчик запросов пользователя к сценариям работы с показаниями счетчиков
 */
@AuditLogger
@TimeProfileLog
@Path("/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"USER"})
@AllArgsConstructor
@NoArgsConstructor
public class ReadingResource {

    @Inject
    private AppUserContext userContext;

    @Inject
    @Named("actualMeterReadingsInteracror")
    private GetActualMeterReadingsInput actualReadingInteractor;

    @Inject
    @Named("meterReadingsHistoryInteractor")
    private ViewMeterReadingsHistoryInput historyInteractor;

    @Inject
    @Named("monthMeterReadingsInteractor")
    private GetMonthMeterReadingsInput monthInteractor;

    @Inject
    @Named("submissionMeterReadingsInteractor")
    private SubmissionMeterReadingsInput submissionInteractor;

    @GET
    @Path("/actual")
    public List<? extends MeterReading> actual() {
        GetActualMeterReadingsInputRequest request = new CoreGetActualMeterReadingsInputRequest(
                userContext.getCurrentUser());

        return actualReadingInteractor.find(request).getMeterReadings();
    }

    @GET
    public List<? extends MeterReading> history() {
        ViewMeterReadingsHistoryInputRequest request = new CoreViewMeterReadingsHistoryInputRequest(
                userContext.getCurrentUser());

        return historyInteractor.find(request).getMeterReadings();
    }

    @GET
    @Path("/month")
    public List<? extends MeterReading> month(@Valid @BeanParam AppMonthReadingRequest request) {
        GetMonthMeterReadingsInputRequest coreRequest = new CoreGetMonthMeterReadingsInputRequest(
                userContext.getCurrentUser(), request.getPeriod());

        return monthInteractor.find(coreRequest).getMeterReadings();
    }

    @POST
    public MeterReading submit(@Valid AppSubmitReadingRequest request) {
        SubmissionMeterReadingsInputRequest coreRequest = CoreSubmissionMeterReadingsInputRequest.builder()
                .value(request.getValue())
                .user(userContext.getCurrentUser())
                .period(request.getPeriod())
                .meterName(request.getMeterName())
                .build();

        return submissionInteractor.submit(coreRequest).getMeterReading();
    }
}
