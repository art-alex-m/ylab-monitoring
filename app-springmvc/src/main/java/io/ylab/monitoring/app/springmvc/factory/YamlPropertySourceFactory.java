package io.ylab.monitoring.app.springmvc.factory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Парсер Yaml конфигурации
 *
 * <p>
 *     <a href="https://www.baeldung.com/spring-reloading-properties">Reloading Properties Files in Spring</a><br>
 *     <a href="https://www.baeldung.com/properties-with-spring">Properties with Spring and Spring Boot</a><br>
 *     <a href="https://www.baeldung.com/spring-yaml-propertysource">PropertySource with YAML Files in Spring Boot</a><br>
 * </p>
 */
@Component
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {

        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        Properties properties = factory.getObject();
        Objects.requireNonNull(encodedResource.getResource().getFilename());
        Objects.requireNonNull(properties);

        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }
}
