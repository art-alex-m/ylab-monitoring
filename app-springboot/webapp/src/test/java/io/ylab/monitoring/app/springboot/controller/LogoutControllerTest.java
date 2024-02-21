package io.ylab.monitoring.app.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.springboot.service.AuthTokenManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = LogoutController.class, excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
class LogoutControllerTest {

    private static final String TEST_ROUTE = "/api/logout";

    @Autowired
    ObjectMapper jsonMapper;

    @MockBean
    AuthTokenManager tokenManager;

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenTokenHeader_whenLogout_thenSuccess() throws Exception {
        String tokenHeader = "test-token-value";


        MvcResult result = mockMvc.perform(post(TEST_ROUTE)
                        .header("Authorization", tokenHeader)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();


        assertThat(result.getResponse().getStatus()).isEqualTo(204);
        verify(tokenManager, times(1)).revokeToken(tokenHeader);
        verifyNoMoreInteractions(tokenManager);
    }

    @Test
    void givenNoTokenHeader_whenLogout_thenSuccess() throws Exception {
        MvcResult result = mockMvc.perform(post(TEST_ROUTE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();


        assertThat(result.getResponse().getStatus()).isEqualTo(204);
        verifyNoInteractions(tokenManager);
    }
}
