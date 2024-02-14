package io.ylab.monitoring.app.springmvc.controller;

import io.ylab.monitoring.app.springmvc.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreViewMetersInputRequest;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Просмотр доступных типов показаний счетчиков
 */
@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Secured("USER")
public class MeterController {

    private final ViewMetersInput metersInteractor;

    private final AppUserContext userContext;

    @GetMapping("/meters")
    public List<? extends Meter> listMeters() {
        ViewMetersInputRequest request = new CoreViewMetersInputRequest(userContext.getCurrentUser());
        return metersInteractor.find(request).getMeters();
    }
}
