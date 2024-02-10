package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.audit.model.AuditDomainUser;
import io.ylab.monitoring.domain.core.model.DomainUser;
import jakarta.security.enterprise.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserContextTest {

    private final UUID testUuid = UUID.randomUUID();

    @Mock
    SecurityContext securityContext;

    @Mock
    Principal principal;

    AppUserContext sut;

    @BeforeEach
    void setUp() {
        sut = new AppUserContext(securityContext);
    }

    @Test
    void givenPrincipal_whenGetCurrentUser_thenSuccessDomainUser() {
        when(securityContext.getCallerPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn(testUuid.toString());

        DomainUser result = sut.getCurrentUser();

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testUuid);
    }

    @Test
    void givenSecurityCtx_whenGetSecurityContext_thenSuccess() {
        SecurityContext result = sut.getSecurityContext();

        assertThat(result).isNotNull().isEqualTo(securityContext);
    }

    @Test
    void givenNullSecurityCtx_whenGetSecurityContext_thenReturnNullUser() {
        sut = new AppUserContext(null);

        DomainUser result = sut.getCurrentUser();

        assertThat(result).isEqualTo(AuditDomainUser.NULL_USER);
    }
}