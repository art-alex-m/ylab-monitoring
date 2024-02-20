package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.app.servlets.component.HttpRequestAttribute;
import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.auth.out.AuthUserLoginInputResponse;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserContextTest {

    @Mock
    HttpServletRequest request;

    AppUserContext sut;

    @BeforeEach
    void setUp() {
        sut = new AppUserContext();
    }

    @Test
    void givenHttpRequestWithPrincipal_whenGetCurrentUser_thenSuccess() {
        UserLoginInputResponse loginInputResponse = new AuthUserLoginInputResponse(UUID.randomUUID(), DomainRole.USER);
        when(request.getAttribute(HttpRequestAttribute.PRINCIPAL)).thenReturn(loginInputResponse);

        DomainUser result = sut.getCurrentUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(loginInputResponse.getId());
    }

    @Test
    void givenHttpRequestEmpty_whenGetCurrentUser_thenSuccess() {
        when(request.getAttribute(HttpRequestAttribute.PRINCIPAL)).thenReturn(null);

        DomainUser result = sut.getCurrentUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(AuditDomainUser.NULL_USER.getId());
    }
}