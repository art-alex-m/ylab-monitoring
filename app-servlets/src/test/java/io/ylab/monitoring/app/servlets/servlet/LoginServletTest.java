package io.ylab.monitoring.app.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.monitoring.app.servlets.config.AppConfiguration;
import io.ylab.monitoring.app.servlets.in.AppLoginRequest;
import io.ylab.monitoring.app.servlets.service.AppValidationService;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.app.servlets.support.TestServletInputStream;
import io.ylab.monitoring.app.servlets.support.TestServletUtils;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {

    private final static String loginJson = "{\"username\": \"user\", \"password\": \"pwd\"}";
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
    PrintWriter printWriter;

    @Mock
    AuthTokenManager tokenManager;

    @Mock
    UserLoginInput loginInput;

    LoginServlet sut;

    @BeforeEach
    void setUp() throws IOException {
        sut = new LoginServlet(objectMapper, loginInput, tokenManager, validationService);
        given(response.getWriter()).willReturn(printWriter);
        given(request.getInputStream()).willReturn(new TestServletInputStream(loginJson));
        given(validationService.validate(any())).willReturn(true);
        given(loginInput.login(any())).willReturn(TestServletUtils.principal);
        given(tokenManager.createToken(TestServletUtils.principal)).willReturn(TestServletUtils.testToken);
    }

    @Test
    void givenLoginRequest_whenDoPost_thenSuccess() throws IOException {
        ArgumentCaptor<AppLoginRequest> loginRequestCaptor = ArgumentCaptor.forClass(AppLoginRequest.class);
        ArgumentCaptor<String> outJsonCaptor = ArgumentCaptor.forClass(String.class);

        Throwable result = catchException(() -> sut.doPost(request, response));

        assertThat(result).isNull();
        verify(validationService, times(1)).validate(any(AppLoginRequest.class));
        verifyNoMoreInteractions(validationService);
        verify(loginInput, times(1)).login(loginRequestCaptor.capture());
        verifyNoMoreInteractions(loginInput);
        AppLoginRequest loginRequest = loginRequestCaptor.getValue();
        assertThat(loginRequest).isNotNull();
        assertThat(loginRequest.getPassword()).isEqualTo(password);
        assertThat(loginRequest.getUsername()).isEqualTo(username);
        verify(response, times(1)).setContentType(TestServletUtils.mediaType);
        verify(response, times(1)).setCharacterEncoding(TestServletUtils.encoding);
        verify(response, times(1)).getWriter();
        verifyNoMoreInteractions(response);
        verify(printWriter, times(1)).print(outJsonCaptor.capture());
        String outJson = outJsonCaptor.getValue();
        assertJson(outJson).containsKeysExactlyInAnyOrder("authorization_token");
        assertJson(outJson).at("/authorization_token").isText(TestServletUtils.testToken);
    }
}