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
import io.ylab.monitoring.app.springboot.out.AppMeterReading;
import io.ylab.monitoring.app.springboot.service.AppUserContext;
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
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Обработчик запросов администратора к сценариям работы с показаниями счетчиков
 */
@Tag(name = OpenapiTag.ADMIN)
@Tag(name = OpenapiTag.READINGS)
@RestController
@RequestMapping(
        value = "/admin/readings",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed("ADMIN")
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


    @Operation(summary = "Get actual meter readings", operationId = "getAdminActualMeterReadings", responses = {
            @ApiResponse(responseCode = "200", description = "Readings",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppMeterReading.class))))
    })
    @GetMapping("/actual")
    public List<? extends MeterReading> actual() {
        GetActualMeterReadingsInputRequest request = new CoreGetActualMeterReadingsInputRequest(
                userContext.getCurrentUser());

        return actualReadingInteractor.find(request).getMeterReadings();
    }


    @Operation(summary = "View history of all users", operationId = "getAdminHistoryMeterReadings", responses = {
            @ApiResponse(responseCode = "200", description = "Readings",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppMeterReading.class))))
    })
    @GetMapping
    public List<? extends MeterReading> history() {
        ViewMeterReadingsHistoryInputRequest request = new CoreViewMeterReadingsHistoryInputRequest(
                userContext.getCurrentUser());

        return historyInteractor.find(request).getMeterReadings();
    }

    @Operation(summary = "View month history of all users", operationId = "getAdminMonthMeterReadings",
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
}
