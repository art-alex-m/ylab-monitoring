package io.ylab.monitoring.app.springboot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springboot.config.OpenapiTag;
import io.ylab.monitoring.app.springboot.in.AppMonthReadingRequest;
import io.ylab.monitoring.app.springboot.in.AppSubmitReadingRequest;
import io.ylab.monitoring.app.springboot.out.AppMeterReading;
import io.ylab.monitoring.app.springboot.service.AppUserContext;
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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Обработчик запросов пользователя к сценариям работы с показаниями счетчиков
 */
@Tag(name = OpenapiTag.USER)
@Tag(name = OpenapiTag.READINGS)
@RestController
@RequestMapping(
        value = "/readings",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed("USER")
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

    @Operation(summary = "Get user actual meter readings", operationId = "getUserActualMeterReadings", responses = {
            @ApiResponse(responseCode = "200", description = "Readings",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppMeterReading.class))))})
    @GetMapping("/actual")
    public List<? extends MeterReading> actual() {
        GetActualMeterReadingsInputRequest request = new CoreGetActualMeterReadingsInputRequest(
                userContext.getCurrentUser());

        return actualReadingInteractor.find(request).getMeterReadings();
    }

    @Operation(summary = "View history for user", operationId = "getUserHistoryMeterReadings", responses = {
            @ApiResponse(responseCode = "200", description = "Readings",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppMeterReading.class))))})
    @GetMapping
    public List<? extends MeterReading> history() {
        ViewMeterReadingsHistoryInputRequest request = new CoreViewMeterReadingsHistoryInputRequest(
                userContext.getCurrentUser());

        return historyInteractor.find(request).getMeterReadings();
    }

    @Operation(summary = "View month history for user", operationId = "getUserMonthMeterReadings",
            parameters = {
                    @Parameter(name = "month", required = true, in = ParameterIn.QUERY),
                    @Parameter(name = "year", in = ParameterIn.QUERY, description = "If omitted use current year")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Readings",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppMeterReading.class))))
            })
    @GetMapping("/month")
    public List<? extends MeterReading> month(@Valid AppMonthReadingRequest request) {
        GetMonthMeterReadingsInputRequest coreRequest = new CoreGetMonthMeterReadingsInputRequest(
                userContext.getCurrentUser(), request.getPeriod());

        return monthInteractor.find(coreRequest).getMeterReadings();
    }

    @Operation(summary = "Submit new reading", responses = {
            @ApiResponse(responseCode = "201", description = "Created meter reading",
                    content = @Content(schema = @Schema(implementation = AppMeterReading.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeterReading submit(@Valid @RequestBody AppSubmitReadingRequest request) {
        SubmissionMeterReadingsInputRequest coreRequest = CoreSubmissionMeterReadingsInputRequest.builder()
                .value(request.getValue())
                .user(userContext.getCurrentUser())
                .period(request.getPeriod())
                .meterName(request.getMeterName())
                .build();

        return submissionInteractor.submit(coreRequest).getMeterReading();
    }
}
