package io.ylab.monitoring.app.servlets.component;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import java.util.UUID;

/**
 * Конфигурация JSONB
 *
 * <p>
 * <a href="https://tomee.apache.org/latest/examples/jsonb-custom-serializer.html">JSON-B Custom Serializer</a><br>
 * <a href="https://stackoverflow.com/questions/24489186/customize-json-serialization-with-jaxrs">Customize JSON serialization with JaxRS</a><br>
 * <a href="https://github.com/payara/Payara/issues/2541">UUIDs not being serialized as expected in JAX-RS</a><br>
 * </p>
 */
@Provider
public class JSONBConfigurationProvider implements ContextResolver<Jsonb> {
    private Jsonb jsonb;

    public JSONBConfigurationProvider() {
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withSerializers(new UuidSerializer());

        jsonb = JsonbBuilder.create(config);
    }

    @Override
    public Jsonb getContext(Class<?> type) {
        return jsonb;
    }


    private static class UuidSerializer implements JsonbSerializer<UUID> {
        @Override
        public void serialize(UUID uuid, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
            if (uuid != null) {
                jsonGenerator.write(uuid.toString());
            }
        }
    }
}
