package io.ylab.monitoring.app.servlets.component;

import io.ylab.monitoring.app.servlets.out.AppViolationError;
import io.ylab.monitoring.domain.core.exception.BusinessLogicMonitoringException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

/**
 * Форматирование бизнес ошибки
 */
@Provider
public class BusinessLogicMonitoringExceptionMapper implements ExceptionMapper<BusinessLogicMonitoringException> {
    @Override
    public Response toResponse(BusinessLogicMonitoringException logicException) {

        AppViolationError error = new AppViolationError(logicException.getClass().getName(), "message",
                logicException.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).entity(List.of(error)).build();
    }
}
