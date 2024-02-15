package io.ylab.monitoring.app.springmvc.config;

import io.ylab.monitoring.app.springmvc.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.mock;

@Configuration
@EnableWebMvc
public class AppTestControllerConfiguration {

    @Bean
    public UserLoginInput userLoginInteractor() {
        return mock(UserLoginInput.class);
    }

    @Bean
    public AuthTokenManager authTokenManager() {
        return mock(AuthTokenManager.class);
    }
}
