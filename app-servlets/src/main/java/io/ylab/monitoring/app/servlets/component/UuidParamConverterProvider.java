package io.ylab.monitoring.app.servlets.component;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.UUID;

@Provider
public class UuidParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type type, Annotation[] annotations) {
        if (UUID.class != rawType) {
            return null;
        }

        return (ParamConverter<T>) new UiidParamConverter();
    }

    private static class UiidParamConverter implements ParamConverter<UUID> {
        @Override
        public UUID fromString(String string) {
            return UUID.fromString(string);
        }

        @Override
        public String toString(UUID uuid) {
            return uuid.toString();
        }
    }
}
