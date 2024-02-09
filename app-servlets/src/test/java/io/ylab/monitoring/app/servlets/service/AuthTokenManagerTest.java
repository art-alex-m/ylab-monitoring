package io.ylab.monitoring.app.servlets.service;

import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
    void givenLength_whenCreateToken_thenSuccess(int length) {
        String result = sut.createToken(length);

        assertThat(result).isNotNull().hasSizeGreaterThan(length);
    }

    @Test
    void givenToken_whenFindByKey_thenSuccess() {
        UUID userId = UUID.randomUUID();
        Mockito.when(response.getId()).thenReturn(userId);
        String token = sut.createToken(response);

        UserLoginInputResponse result = sut.findByKey(token);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void givenToken_whenRevokeToken_thenSuccess() {
        UUID userId = UUID.randomUUID();
        Mockito.when(response.getId()).thenReturn(userId);
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
        Mockito.when(response.getId()).thenReturn(userId);

        String result = sut.createToken(response);

        assertThat(result).isNotEmpty();
    }

    @Test
    void givenSameUser_whenCreate_thenSameToken() {
        UUID userId = UUID.randomUUID();
        Mockito.when(response.getId()).thenReturn(userId);
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