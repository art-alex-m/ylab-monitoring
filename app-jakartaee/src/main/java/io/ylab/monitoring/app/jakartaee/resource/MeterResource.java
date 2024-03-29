package io.ylab.monitoring.app.jakartaee.resource;

import io.ylab.monitoring.app.jakartaee.interceptor.AuditLogger;
import io.ylab.monitoring.app.jakartaee.interceptor.TimeProfileLog;
import io.ylab.monitoring.app.jakartaee.service.AppUserContext;
import io.ylab.monitoring.core.in.CoreViewMetersInputRequest;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.Meter;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Просмотр доступных типов показаний счетчиков
 */
@AuditLogger
@Path("/meters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"USER"})
@AllArgsConstructor
@NoArgsConstructor
public class MeterResource {

    @Inject
    @Named("appUserViewMetersInput")
    private ViewMetersInput metersInteractor;

    @Inject
    private AppUserContext userContext;

    /**
     * FIXME: При аннотировании @TimeProfileLog метода ресурса интерсептор не срабатывает
     */
    @TimeProfileLog
    @GET
    public List<? extends Meter> listMeters() {
        ViewMetersInputRequest request = new CoreViewMetersInputRequest(userContext.getCurrentUser());
        return metersInteractor.find(request).getMeters();
    }
}
