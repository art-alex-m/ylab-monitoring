package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppRegistrationRequest;
import io.ylab.monitoring.app.servlets.service.AppValidationService;
import io.ylab.monitoring.app.servlets.support.TestServletInputStream;
import io.ylab.monitoring.domain.auth.boundary.UserRegistrationInput;
import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServletTest {

    private final static String registrationJson = "{\"username\": \"user\", \"password\": \"pwd\"}";
    private final static String username = "user";
    private final static String password = "pwd";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    ObjectMapper objectMapper = AppConfiguration.REGISTRY.objectMapper();

    @Mock
    AppValidationService validationService;

    @Mock
    UserRegistrationInput registrationInput;

    UserRegistrationServlet sut;

    @BeforeEach
    void setUp() throws IOException {
        sut = new UserRegistrationServlet(objectMapper, registrationInput, validationService);
        given(request.getInputStream()).willReturn(new TestServletInputStream(registrationJson));
        given(validationService.validate(any())).willReturn(true);
    }

    @Test
    void givenRegisterRequest_whenDoPost_thenSuccess() throws IOException {
        ArgumentCaptor<UserRegistrationInputRequest> registerRequestCaptor = ArgumentCaptor.forClass(
                UserRegistrationInputRequest.class);

        Throwable result = catchException(() -> sut.doPost(request, response));

        assertThat(result).isNull();
        verify(validationService, times(1)).validate(any(AppRegistrationRequest.class));
        verifyNoMoreInteractions(validationService);
        verify(registrationInput, times(1)).register(registerRequestCaptor.capture());
        verifyNoMoreInteractions(registrationInput);
        UserRegistrationInputRequest regRequest = registerRequestCaptor.getValue();
        assertThat(regRequest).isNotNull();
        assertThat(regRequest.getPassword()).isEqualTo(password);
        assertThat(regRequest.getUsername()).isEqualTo(username);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
        verifyNoMoreInteractions(response);
    }
}