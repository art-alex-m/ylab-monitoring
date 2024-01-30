package io.ylab.monitoring.auth.service;

import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AuthPasswordEncoderTest {

    private final static String testPassword = "123456";

    private final static String testSalt = "test-salt";

    private final static String expectedTestWithTestSalt = "CDjNpsPUEY1QDcNMcLSAzYXQMVNacj5N+mLFJLesN+HtIGBjZWZinSM+NZ4eqarlFmTUH5PjgU8COALj6zUHhg==";


    public static Stream<Arguments> givenPasswordAndSalt_whenMatches_thenSuccess() {
        return Stream.of(
                Arguments.of(testPassword, testSalt, expectedTestWithTestSalt, true),
                Arguments.of(null, testSalt, expectedTestWithTestSalt, false),
                Arguments.of(testPassword, testPassword, null, false),
                Arguments.of("", testSalt, expectedTestWithTestSalt, false),
                Arguments.of(testPassword, "", expectedTestWithTestSalt, false),
                Arguments.of("", "", "", false),
                Arguments.of(testPassword, testPassword, "", false),
                Arguments.of(testPassword, "98765432", expectedTestWithTestSalt, false),
                Arguments.of("", "98765432", "", false)
        );
    }

    @Test
    void givenStringSalt_whenSetSalt_thenSuccess() {
        String someSalt = "some-salt";
        AuthPasswordEncoder passwordEncoder = new AuthPasswordEncoder();

        AuthPasswordEncoder result = passwordEncoder.setSalt(someSalt);

        assertThat(result).isNotNull().isInstanceOf(PasswordEncoder.class);
    }

    @Test
    void givenNullSalt_whenSetSalt_thenException() {
        AuthPasswordEncoder passwordEncoder = new AuthPasswordEncoder();

        Throwable result = catchThrowable(() -> passwordEncoder.setSalt(null));

        assertThat(result).isNotNull().isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenTestSaltAndTestPassword_whenEncode_thenSuccess() {
        PasswordEncoder passwordEncoder = new AuthPasswordEncoder().setSalt(testSalt);

        String result = passwordEncoder.encode(testPassword);

        assertThat(result).isNotNull().isEqualTo(expectedTestWithTestSalt);
    }

    @ParameterizedTest
    @MethodSource
    void givenPasswordAndSalt_whenMatches_thenSuccess(String password, String salt, String hash, boolean expected) {
        PasswordEncoder passwordEncoder = new AuthPasswordEncoder().setSalt(salt);

        boolean result = passwordEncoder.matches(password, hash);

        assertThat(result).isEqualTo(expected);
    }
}