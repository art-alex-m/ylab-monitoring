package io.ylab.monitoring.app.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springboot.config.OpenapiTag;
import io.ylab.monitoring.app.springboot.out.AppMeter;
import io.ylab.monitoring.app.springboot.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreViewMetersInputRequest;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Просмотр доступных типов показаний счетчиков
 */
@Tag(name = OpenapiTag.USER)
@Tag(name = OpenapiTag.READINGS)
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed("USER")
public class MeterController {

    private final ViewMetersInput metersInteractor;

    private final AppUserContext userContext;

    @Operation(summary = "Show meters", operationId = "getUserMeters", responses = {
            @ApiResponse(responseCode = "200", description = "Meters list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppMeter.class))))
    })
    @GetMapping("/meters")
    public List<? extends Meter> listMeters() {
        ViewMetersInputRequest request = new CoreViewMetersInputRequest(userContext.getCurrentUser());
        return metersInteractor.find(request).getMeters();
    }
}
