package io.ylab.monitoring.app.springmvc.controller;

import io.ylab.monitoring.app.springmvc.config.AppMonitoringWebAppInitializer;
import io.ylab.monitoring.app.springmvc.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppMonitoringWebAppInitializer.class})
class LoginControllerTest {

    @Mock
    UserLoginInput loginInteractor;

    @Mock
    AuthTokenManager tokenManager;

    @Mock
    UserLoginInputResponse response;

    @InjectMocks
    LoginController sut;

    @Autowired
    private ApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //mockMvc = MockMvcBuilders.webAppContextSetup((AnnotationConfigWebApplicationContext) applicationContext).build();
    }

    @Test
    void login() {
        return;
    }
}