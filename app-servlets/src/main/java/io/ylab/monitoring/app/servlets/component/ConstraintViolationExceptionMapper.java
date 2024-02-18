package io.ylab.monitoring.app.servlets.component;

import io.ylab.monitoring.app.servlets.out.AppViolationError;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Set;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException ex) {

        Set<AppViolationError> constraintViolations = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String className = violation.getLeafBean().toString().split("@")[0];
                    String message = violation.getMessage();
                    String fieldName = violation.getPropertyPath().toString().split("\\.")[2];

                    return new AppViolationError(className, fieldName, message);
                })
                .collect(Collectors.toSet());

        return Response.status(Response.Status.BAD_REQUEST).entity(constraintViolations).build();
    }
}
