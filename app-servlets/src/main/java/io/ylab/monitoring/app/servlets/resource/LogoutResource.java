package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Path("/logout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResource {

    @Inject
    private AuthTokenManager tokenManager;

    @POST
    public Response logout(@HeaderParam("Authorization") String authToken) {
        if (authToken != null) {
            tokenManager.revokeToken(authToken);
        }

        return Response.ok("{\"logout\": true}").build();
    }
}
