package io.ylab.monitoring.app.springmvc.controller;


import io.ylab.monitoring.app.springmvc.in.AppMonthReadingRequest;
import io.ylab.monitoring.app.springmvc.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreGetActualMeterReadingsInputRequest;
import io.ylab.monitoring.core.in.CoreGetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.core.in.CoreViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.boundary.GetActualMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.GetMonthMeterReadingsInput;
import io.ylab.monitoring.domain.core.boundary.ViewMeterReadingsHistoryInput;
import io.ylab.monitoring.domain.core.in.GetActualMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.GetMonthMeterReadingsInputRequest;
import io.ylab.monitoring.domain.core.in.ViewMeterReadingsHistoryInputRequest;
import io.ylab.monitoring.domain.core.model.MeterReading;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Обработчик запросов администратора к сценариям работы с показаниями счетчиков
 */
@RestController
@RequestMapping(
        value = "/admin/readings",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Secured("ADMIN")
public class AdminReadingController {

    private final AppUserContext userContext;

    private final GetActualMeterReadingsInput actualReadingInteractor;

    private final ViewMeterReadingsHistoryInput historyInteractor;

    private final GetMonthMeterReadingsInput monthInteractor;

    public AdminReadingController(
            AppUserContext userContext,
            @Qualifier("adminActualMeterReadingsInteractor") GetActualMeterReadingsInput actualReadingInteractor,
            @Qualifier("adminMeterReadingsHistoryInteractor") ViewMeterReadingsHistoryInput historyInteractor,
            @Qualifier("adminMonthMeterReadingsInteractor") GetMonthMeterReadingsInput monthInteractor) {
        this.userContext = userContext;
        this.actualReadingInteractor = actualReadingInteractor;
        this.historyInteractor = historyInteractor;
        this.monthInteractor = monthInteractor;
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
}
