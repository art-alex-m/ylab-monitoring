package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.app.servlets.in.AppLoginRequest;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
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
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    private UserLoginInput loginInteractor;

    @POST
    public Response login(@Valid AppLoginRequest appLoginRequest) {
        UserLoginInputResponse response = loginInteractor.login(appLoginRequest);
        return Response.ok(response).build();
    }
}
