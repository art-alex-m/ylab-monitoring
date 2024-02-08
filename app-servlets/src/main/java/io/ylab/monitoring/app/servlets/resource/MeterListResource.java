package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.core.in.CoreViewMetersInputRequest;
import io.ylab.monitoring.core.model.CoreDomainUser;
import io.ylab.monitoring.domain.core.boundary.ViewMetersInput;
import io.ylab.monitoring.domain.core.in.ViewMetersInputRequest;
import io.ylab.monitoring.domain.core.model.DomainUser;
import io.ylab.monitoring.domain.core.model.Meter;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Dependent
@Path("/meter/list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("USER")
public class MeterListResource {

    @Inject
    @Named("appUserViewMetersInput")
    private ViewMetersInput metersInteractor;

    @GET
    public List<Meter> listMeters() {
        DomainUser user = new CoreDomainUser(UUID.randomUUID());
        ViewMetersInputRequest request = new CoreViewMetersInputRequest(user);
        return metersInteractor.find(request).getMeters();
    }
}
