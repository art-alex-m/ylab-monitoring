package io.ylab.monitoring.app.springmvc.controller;


import io.ylab.monitoring.app.springmvc.in.AppMonthReadingRequest;
import io.ylab.monitoring.app.springmvc.in.AppSubmitReadingRequest;
import io.ylab.monitoring.app.springmvc.service.AppUserContext;
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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Обработчик запросов пользователя к сценариям работы с показаниями счетчиков
 */
@RestController
@RequestMapping(
        value = "/readings",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Secured("USER")
public class ReadingController {

    private final AppUserContext userContext;

    private final GetActualMeterReadingsInput actualReadingInteractor;

    private final ViewMeterReadingsHistoryInput historyInteractor;

    private final GetMonthMeterReadingsInput monthInteractor;

    private final SubmissionMeterReadingsInput submissionInteractor;

    public ReadingController(
            AppUserContext userContext,
            @Qualifier("actualMeterReadingsInteracror") GetActualMeterReadingsInput actualReadingInteractor,
            @Qualifier("meterReadingsHistoryInteractor") ViewMeterReadingsHistoryInput historyInteractor,
            @Qualifier("monthMeterReadingsInteractor") GetMonthMeterReadingsInput monthInteractor,
            @Qualifier("submissionMeterReadingsInteractor") SubmissionMeterReadingsInput submissionInteractor) {
        this.userContext = userContext;
        this.actualReadingInteractor = actualReadingInteractor;
        this.historyInteractor = historyInteractor;
        this.monthInteractor = monthInteractor;
        this.submissionInteractor = submissionInteractor;
    }

    @GetMapping("/actual")
    public List<? extends MeterReading> actual() {
        GetActualMeterReadingsInputRequest request = new CoreGetActualMeterReadingsInputRequest(
                userContext.getCurrentUser());

        return actualReadingInteractor.find(request).getMeterReadings();
    }

    @GetMapping
    public List<? extends MeterReading> history() {
        ViewMeterReadingsHistoryInputRequest request = new CoreViewMeterReadingsHistoryInputRequest(
                userContext.getCurrentUser());

        return historyInteractor.find(request).getMeterReadings();
    }

    @GetMapping("/month")
    public List<? extends MeterReading> month(@Valid AppMonthReadingRequest request) {
        GetMonthMeterReadingsInputRequest coreRequest = new CoreGetMonthMeterReadingsInputRequest(
                userContext.getCurrentUser(), request.getPeriod());

        return monthInteractor.find(coreRequest).getMeterReadings();
    }

    @PostMapping
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
