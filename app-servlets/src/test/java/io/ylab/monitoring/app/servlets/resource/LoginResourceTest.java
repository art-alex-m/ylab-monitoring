package io.ylab.monitoring.app.servlets.resource;

import io.ylab.monitoring.app.servlets.in.AppLoginRequest;
import io.ylab.monitoring.app.servlets.out.AppAuthToken;
import io.ylab.monitoring.app.servlets.service.AuthTokenManager;
import io.ylab.monitoring.domain.auth.boundary.UserLoginInput;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginResourceTest {

    LoginResource sut;

    @Mock
    UserLoginInput loginInteractor;

    @Mock
    AuthTokenManager tokenManager;

    @Mock
    UserLoginInputResponse response;

    @BeforeEach
    void setUp() {
        sut = new LoginResource(loginInteractor, tokenManager);
    }

    @Test
    void givenLoginRequest_whenLogin_thenSuccess() {
        AppLoginRequest request = new AppLoginRequest("test-user", "test-password");
        given(loginInteractor.login(request)).willReturn(response);
        given(tokenManager.createToken(response)).willReturn("test-token");

        AppAuthToken result = sut.login(request);

        assertThat(result).isNotNull();
        assertThat(result.getAuthorizationToken()).isEqualTo("test-token");
        verify(loginInteractor, times(1)).login(any(AppLoginRequest.class));
        verify(tokenManager, times(1)).createToken(any(UserLoginInputResponse.class));
    }
}