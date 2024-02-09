package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.app.servlets.in.AppRegistrationRequest;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Dependent
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class RegistrationResource {

    @Inject
    UserRegistrationInput registrationInteractor;

    @POST
    public Response register(@Valid AppRegistrationRequest request) {
        registrationInteractor.register(request);
        return Response.ok("{\"registered\": true}").build();
    }
}
