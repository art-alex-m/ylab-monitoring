package io.ylab.monitoring.app.servlets.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;

@Path("/hello-world")
public class HelloResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> hello() {
        return new HashMap<>() {{
            put("text", "hello, world!");
        }};
    }
}