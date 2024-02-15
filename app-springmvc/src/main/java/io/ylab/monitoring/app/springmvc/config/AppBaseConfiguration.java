package io.ylab.monitoring.app.springmvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.springmvc.factory.YamlPropertySourceFactory;
import io.ylab.monitoring.app.springmvc.model.AppDbProperties;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryRepository;
import io.ylab.monitoring.db.jdbc.repository.SqlQueryResourcesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@EnableWebMvc
@EnableAspectJAutoProxy
@Configuration
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertySourceFactory.class)
public class AppBaseConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true).build();
        converters.add(new MappingJackson2HttpMessageConverter(builder));
    }

    @Bean("db")
    public Connection connection(AppDbProperties dbProperties) throws SQLException {
        return DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(),
                dbProperties.getPassword());
    }

    @Bean("appSqlQueryRepository")
    public SqlQueryRepository sqlQueryRepository() {
        return new SqlQueryResourcesRepository();
    }
}
