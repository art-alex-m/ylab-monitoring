package io.ylab.monitoring.app.springboot.service;

import io.ylab.monitoring.app.springboot.model.AppUserDetails;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import io.ylab.monitoring.domain.core.model.DomainRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthTokenManagerTest {

    @Mock
    UserLoginInputResponse response;

    AuthTokenManager sut;

    @BeforeEach
    void setUp() {
        sut = new AuthTokenManager();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 15, 48})
    void givenTokenLength_whenCreateToken_thenSuccess(int length) {
        String result = sut.createToken(length);

        assertThat(result).isNotNull().hasSizeGreaterThan(length);
    }

    @Test
    void givenToken_whenFindByKey_thenSuccess() {
        UUID userId = UUID.randomUUID();
        when(response.getId()).thenReturn(userId);
        when(response.getRole()).thenReturn(DomainRole.USER);
        String token = sut.createToken(response);

        AppUserDetails result = sut.findByKey(token);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(response.getId());
        assertThat(result.getUsername()).isEqualTo(response.getId().toString());
        assertThat(result.getAuthorities())
                .hasSize(1)
                .map(GrantedAuthority::getAuthority)
                .isEqualTo(List.of("ROLE_" + DomainRole.USER.name()));
    }

    @Test
    void givenToken_whenRevokeToken_thenSuccess() {
        UUID userId = UUID.randomUUID();
        when(response.getId()).thenReturn(userId);
        when(response.getRole()).thenReturn(DomainRole.USER);
        String token = sut.createToken(response);

        boolean result = sut.revokeToken(token);

        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bearer ", "", " ", "some-undefined-token"})
    @NullSource
    void givenBadToken_whenRevokeToken_thenSuccess(String badToken) {
        boolean result = sut.revokeToken(badToken);

        assertThat(result).isFalse();
    }

    @Test
    void givenUserLogin_whenCreateToken_thenSuccess() {
        UUID userId = UUID.randomUUID();
        when(response.getId()).thenReturn(userId);
        when(response.getRole()).thenReturn(DomainRole.USER);

        String result = sut.createToken(response);

        assertThat(result).isNotEmpty();
    }

    @Test
    void givenSameUser_whenCreate_thenSameToken() {
        UUID userId = UUID.randomUUID();
        when(response.getId()).thenReturn(userId);
        when(response.getRole()).thenReturn(DomainRole.USER);
        String token = sut.createToken(response);

        String result = sut.createToken(response);

        assertThat(result).isNotEmpty().isEqualTo(token);
    }

    @ParameterizedTest
    @CsvSource({"Bearer A123456,A123456", "B987654, B987654", ",''", "'',''"})
    void givenTokenStrings_whenPrepareToken_thenExpected(String rawToken, String expected) {
        String result = sut.prepareToken(rawToken);

        assertThat(result).isEqualTo(expected);
    }
}
