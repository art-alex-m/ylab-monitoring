package io.ylab.monitoring.app.servlets.servlet;

import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.app.servlets.support.TestServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {

    private final static String authorizationHeader = "Authorization";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    AuthTokenManager tokenManager;

    LogoutServlet sut;

    @BeforeEach
    void setUp() {
        sut = new LogoutServlet(tokenManager);
    }

    @ParameterizedTest
    @ValueSource(strings = {TestServletUtils.testToken, ""})
    @NullSource
    void givenHeader_whenDoPost_thenSuccess(String testToken) {
        given(request.getHeader(authorizationHeader)).willReturn(testToken);

        Throwable result = catchThrowable(() -> sut.doPost(request, response));

        assertThat(result).isNull();
        verify(tokenManager, times(1)).revokeToken(testToken);
        verifyNoMoreInteractions(tokenManager);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
        verifyNoMoreInteractions(response);
    }
}