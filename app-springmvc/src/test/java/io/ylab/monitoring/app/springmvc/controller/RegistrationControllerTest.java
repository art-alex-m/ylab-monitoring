package io.ylab.monitoring.app.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.springmvc.in.AppRegistrationRequest;
import io.ylab.monitoring.app.springmvc.out.AppError;
import io.ylab.monitoring.app.springmvc.support.MockBean;
import io.ylab.monitoring.app.springmvc.support.MockMvcTest;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.exception.UserExistsException;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@MockMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    ObjectMapper jsonMapper;

    @MockBean
    UserRegistrationInput interactor;

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenRequest_whenRegister_thenSuccess() throws Exception {
        String username = "test-user";
        String password = "test-password";
        ArgumentCaptor<UserRegistrationInputRequest> inputRequestCaptor = ArgumentCaptor.forClass(
                UserRegistrationInputRequest.class);
        AppRegistrationRequest request = new AppRegistrationRequest(username, password);

        MvcResult result = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andReturn();

        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(204);
        verify(interactor, times(1)).register(inputRequestCaptor.capture());
        UserRegistrationInputRequest capturedRequest = inputRequestCaptor.getValue();
        assertThat(capturedRequest).isNotNull();
        assertThat(capturedRequest.getPassword()).isEqualTo(password);
        assertThat(capturedRequest.getUsername()).isEqualTo(username);
        verifyNoMoreInteractions(interactor);
    }

    @Test
    void givenExistsUserError_whenRegister_thenBabRequest() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        AppRegistrationRequest request = new AppRegistrationRequest(username, password);
        UserExistsException exception = new UserExistsException(username);
        given(interactor.register(any(UserRegistrationInputRequest.class))).willThrow(exception);

        MvcResult result = mockMvc.perform(
                        post("/register")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(jsonMapper.writeValueAsString(request)))
                .andReturn();

        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        AppError resultObject = jsonMapper.readValue(result.getResponse().getContentAsString(), AppError.class);
        assertThat(exception.getMessage()).isNotEmpty();
        assertThat(resultObject.getClassName()).isEqualTo(exception.getClass().getName());
        assertThat(resultObject.getMessage()).isEqualTo(exception.getMessage());
        verify(interactor, times(1)).register(any(UserRegistrationInputRequest.class));
        verifyNoMoreInteractions(interactor);
    }
}
