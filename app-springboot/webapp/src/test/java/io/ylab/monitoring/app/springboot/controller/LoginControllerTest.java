package io.ylab.monitoring.app.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.springboot.in.AppLoginRequest;
import io.ylab.monitoring.app.springboot.out.AppAuthToken;
import io.ylab.monitoring.app.springboot.out.AppError;
import io.ylab.monitoring.app.springboot.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.exception.UserNotFoundException;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = LoginController.class, excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
class LoginControllerTest {

    private static final String TEST_ROUTE = "/api/login";

    @Autowired
    ObjectMapper jsonMapper;

    @MockBean(name = "userLoginInteractor")
    UserLoginInput userLoginInteractor;

    @MockBean
    AuthTokenManager tokenManager;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserLoginInputResponse response;

    @Test
    void givenRequest_whenLogin_thenSuccess() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        String tokenValue = "test-token-value";
        given(userLoginInteractor.login(any(UserLoginInputRequest.class))).willReturn(response);
        given(tokenManager.createToken(response)).willReturn(tokenValue);
        AppLoginRequest loginRequest = new AppLoginRequest(username, password);
        ArgumentCaptor<UserLoginInputRequest> loginInputCaptor = ArgumentCaptor.forClass(UserLoginInputRequest.class);


        MvcResult result = mockMvc.perform(
                        post(TEST_ROUTE)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(jsonMapper.writeValueAsString(loginRequest)))
                .andReturn();


        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(result.getResponse().getContentAsString()).contains("authorization_token");
        AppAuthToken resultToken = jsonMapper.readValue(result.getResponse().getContentAsString(), AppAuthToken.class);
        assertThat(resultToken.getAuthorizationToken()).isEqualTo(tokenValue);
        verify(userLoginInteractor, times(1)).login(loginInputCaptor.capture());
        UserLoginInputRequest inputRequest = loginInputCaptor.getValue();
        assertThat(inputRequest).isNotNull();
        assertThat(inputRequest.getPassword()).isEqualTo(password);
        assertThat(inputRequest.getUsername()).isEqualTo(username);
        verify(tokenManager, times(1)).createToken(response);
        verifyNoMoreInteractions(tokenManager);
        verifyNoMoreInteractions(userLoginInteractor);
    }

    @Test
    void givenBusinessError_whenLogin_thenBabRequest() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        AppLoginRequest loginRequest = new AppLoginRequest(username, password);
        UserNotFoundException exception = new UserNotFoundException(username);
        given(userLoginInteractor.login(any(UserLoginInputRequest.class))).willThrow(exception);

        MvcResult result = mockMvc.perform(
                        post(TEST_ROUTE)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(jsonMapper.writeValueAsString(loginRequest)))
                .andReturn();

        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        AppError resultObject = jsonMapper.readValue(result.getResponse().getContentAsString(), AppError.class);
        assertThat(resultObject.getClassName()).isEqualTo(exception.getClass().getName());
        assertThat(resultObject.getMessage()).isEqualTo(exception.getMessage());
        verifyNoInteractions(tokenManager);
    }

    @Test
    void givenBadRequest_whenLogin_thenBabRequest() throws Exception {
        String username = "testUser";
        String password = "";
        AppLoginRequest loginRequest = new AppLoginRequest(username, password);

        MvcResult result = mockMvc.perform(
                        post(TEST_ROUTE)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(jsonMapper.writeValueAsString(loginRequest)))
                .andReturn();

        assertThat(result).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        AppError resultObject = jsonMapper.readValue(result.getResponse().getContentAsString(), AppError.class);
        assertThat(resultObject.getClassName()).isEqualTo(
                "org.springframework.web.bind.MethodArgumentNotValidException");
        assertThat(resultObject.getField()).isEqualTo("password");
        assertThat(resultObject.getMessage()).isEqualTo("must not be empty");
        verifyNoInteractions(tokenManager);
        verifyNoInteractions(userLoginInteractor);
    }
}
