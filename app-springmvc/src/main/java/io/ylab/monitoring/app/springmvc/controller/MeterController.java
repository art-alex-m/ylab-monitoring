package io.ylab.monitoring.app.springmvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.monitoring.app.springmvc.config.OpenapiTag;
import io.ylab.monitoring.app.springmvc.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreViewMetersInputRequest;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Просмотр доступных типов показаний счетчиков
 */
@Path("/meters")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Tag(name = OpenapiTag.USER)
@Tag(name = OpenapiTag.READINGS)
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed("USER")
public class MeterController {

    private final ViewMetersInput metersInteractor;

    private final AppUserContext userContext;

    @GET
    @Operation(summary = "Show meters", operationId = "getUserMeters")
    @GetMapping("/meters")
    public List<? extends Meter> listMeters() {
        ViewMetersInputRequest request = new CoreViewMetersInputRequest(userContext.getCurrentUser());
        return metersInteractor.find(request).getMeters();
    }
}
