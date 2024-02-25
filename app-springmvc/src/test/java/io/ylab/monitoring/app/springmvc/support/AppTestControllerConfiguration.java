package io.ylab.monitoring.app.springmvc.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class AppTestControllerConfiguration {

    @Bean
    public MockMvc createMockMvc(WebApplicationContext applicationContext) {
        return MockMvcBuilders.webAppContextSetup(applicationContext)
                .build();
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
