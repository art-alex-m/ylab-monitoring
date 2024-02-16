package io.ylab.monitoring.app.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.springmvc.service.AuthTokenManager;
import io.ylab.monitoring.app.springmvc.support.MockBean;
import io.ylab.monitoring.app.springmvc.support.MockMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@MockMvcTest(LogoutController.class)
class LogoutControllerTest {

    @Autowired
    ObjectMapper jsonMapper;

    @MockBean
    AuthTokenManager tokenManager;

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenTokenHeader_whenLogout_thenSuccess() throws Exception {
        String tokenHeader = "test-token-value";

        MvcResult result = mockMvc.perform(post("/logout")
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
        MvcResult result = mockMvc.perform(post("/logout")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(204);
        verifyNoInteractions(tokenManager);
    }
}
