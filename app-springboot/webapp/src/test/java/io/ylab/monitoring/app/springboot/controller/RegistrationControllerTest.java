package io.ylab.monitoring.app.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.springboot.in.AppRegistrationRequest;
import io.ylab.monitoring.app.springboot.out.AppError;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.exception.UserExistsException;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = RegistrationController.class, excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
class RegistrationControllerTest {

    private static final String TEST_ROUTE = "/api/register";

    @Autowired
    ObjectMapper jsonMapper;

    @MockBean(name = "userRegistrationInteractor")
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


        MvcResult result = mockMvc.perform(post(TEST_ROUTE)
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
                        post(TEST_ROUTE)
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
